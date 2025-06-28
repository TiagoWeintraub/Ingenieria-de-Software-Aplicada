#!/bin/bash

echo "=== PRUEBA COMPLETA DE LOGGING ==="
echo ""

# Obtener token de autenticación
echo "🔐 Obteniendo token de autenticación..."
TOKEN=$(curl -s -X POST "http://localhost:8080/api/authenticate" \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin","rememberMe":false}' | jq -r '.id_token')

if [ "$TOKEN" = "null" ] || [ -z "$TOKEN" ]; then
    echo "❌ Error al obtener token de autenticación"
    exit 1
fi

echo "✅ Token obtenido exitosamente"
echo ""

# Crear una nueva expense
echo "💰 Creando nueva expense..."
TIMESTAMP=$(date -u +"%Y-%m-%dT%H:%M:%SZ")
RESPONSE=$(curl -s -X POST "http://localhost:8080/api/expenses" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d "{
    \"description\": \"Prueba Logging - $TIMESTAMP\",
    \"amount\": 1234.56,
    \"date\": \"$TIMESTAMP\"
  }")

echo "Respuesta: $RESPONSE"
echo ""

# Esperar a que se procesen los logs
echo "⏳ Esperando a que se procesen los logs..."
sleep 3

# Verificar logs recientes
echo "📋 Logs más recientes en Elasticsearch:"
curl -s -X GET "localhost:9200/expenses-logs-*/_search" \
  -H "Content-Type: application/json" \
  -d '{
    "size": 5,
    "sort": [{"@timestamp": {"order": "desc"}}],
    "_source": ["@timestamp", "level", "logger_name", "message", "log_type"]
  }' | jq -r '.hits.hits[] | "\(.["_source"]["@timestamp"]) [\(.["_source"]["level"])] \(.["_source"]["log_type"] // "unknown"): \(.["_source"]["message"][:80])..."'

echo ""

# Contar logs por tipo
echo "📊 Estadísticas de logs:"
curl -s -X GET "localhost:9200/expenses-logs-*/_search" \
  -H "Content-Type: application/json" \
  -d '{
    "size": 0,
    "aggs": {
      "by_level": {
        "terms": {
          "field": "level",
          "size": 10
        }
      },
      "by_type": {
        "terms": {
          "field": "log_type",
          "size": 10
        }
      }
    }
  }' | jq -r '
    .aggregations.by_level.buckets[] | "  Nivel \(.key): \(.doc_count) logs"
  ' && echo "" && \
curl -s -X GET "localhost:9200/expenses-logs-*/_search" \
  -H "Content-Type: application/json" \
  -d '{
    "size": 0,
    "aggs": {
      "by_type": {
        "terms": {
          "field": "log_type",
          "size": 10
        }
      }
    }
  }' | jq -r '
    .aggregations.by_type.buckets[] | "  Tipo \(.key): \(.doc_count) logs"
  '

echo ""
echo "🎯 ¡Prueba completa! Revisa los logs en Kibana: http://localhost:5601"
