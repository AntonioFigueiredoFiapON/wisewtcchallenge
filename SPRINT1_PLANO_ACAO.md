# Sprint 1 - Plano de Ação

## 🎯 Objetivo
Implementar aplicativo móvel nativo Android com todas as funcionalidades da Sprint 1.

## ✅ Status Atual
- ✅ Projeto configurado (Java 21, Kotlin 1.9.22, KSP)
- ✅ Estrutura base (Hilt, Room, Compose)
- ✅ Telas básicas criadas (mas podem estar incompletas)
- ⚠️ App crashando na inicialização (precisa correção)

## 🔧 Correções Imediatas Necessárias

### 1. Investigar Crash na Inicialização
- [ ] Verificar logs do crash (Logcat)
- [ ] Verificar se `google-services.json` existe
- [ ] Verificar se todas as telas estão implementadas
- [ ] Verificar dependências faltando

### 2. Corrigir Dependências Críticas
- [ ] Verificar se Firebase está configurado corretamente
- [ ] Adicionar tratamento de erros nas telas
- [ ] Garantir que todas as telas têm implementação mínima

## 📋 Funcionalidades Sprint 1 - Checklist

### 1. Autenticação ✅ (Parcial)
- [x] Tela de Login criada
- [ ] Autenticação real (Firebase Auth ou mock)
- [ ] Persistência de sessão
- [ ] Logout
- [ ] Diferenciação Operador/Cliente

### 2. Chat Integrado ao CRM ⚠️ (Em progresso)
- [x] Tela de conversas criada
- [x] Tela de chat criada
- [ ] Conversas 1:1 funcionais
- [ ] Conversas por grupo/segmento
- [ ] Push notifications (Firebase Messaging)
- [ ] Popup in-app para novas mensagens
- [ ] Modelo flexível de mensagem implementado

### 3. CRM no App (Visão Operador) ⚠️ (Em progresso)
- [x] Tela de clientes criada
- [ ] Lista de clientes com dados mockados/reais
- [ ] Busca de clientes
- [ ] Filtros (tags/score/status)
- [ ] Anotações rápidas por cliente
- [ ] Detalhes do cliente

### 4. Campanhas Express ⚠️ (Em progresso)
- [x] Tela de campanhas criada
- [ ] Lista de campanhas
- [ ] Criação de campanha express
- [ ] Envio para segmentos
- [ ] Modelo flexível de mensagem (com actions, URLs)
- [ ] Visualização de campanhas para cliente

### 5. Usabilidade Diferenciada ❌ (Não iniciado)
- [ ] Comandos rápidos "/" (/promo, /boleto, /agradecer)
- [ ] Gestos inteligentes (swipe para marcar importante)
- [ ] Gestos para criar tarefa
- [ ] Deeplinks internos (abrir perfil, última compra, etc.)

## 🏗️ Arquitetura a Implementar

### Modelo de Mensagem Flexível
```kotlin
data class MessageModel(
    val id: String,
    val title: String,
    val body: String,
    val url: String?,
    val actions: List<MessageAction>,
    val actionUrls: Map<String, String>,
    val type: MessageType, // PROMOTION, CAMPAIGN, BANNER, EVENT
    val segment: String?,
    val scheduledAt: Date?,
    val sentAt: Date?
)

data class MessageAction(
    val action: String, // "btn1", "btn2", etc.
    val title: String
)
```

### Comandos Rápidos "/"
- `/promo` - Criar promoção rápida
- `/boleto` - Enviar lembrete de boleto
- `/agradecer` - Mensagem de agradecimento

### Gestos Inteligentes
- Swipe direito → Marcar como importante
- Swipe esquerdo → Criar tarefa
- Long press → Menu de ações

### Deeplinks
- `wtc://crm/client/{id}` - Abrir perfil do cliente
- `wtc://crm/campaign/{id}` - Abrir campanha
- `wtc://crm/purchase/{id}` - Última compra

## 🔄 Próximos Passos (Ordem de Prioridade)

1. **Corrigir crash** - Investigar e resolver problema de inicialização
2. **Autenticação completa** - Implementar login real/mock funcional
3. **Chat básico** - 1:1 funcionando com mensagens mockadas
4. **Campanhas** - Criar e visualizar campanhas com modelo flexível
5. **CRM Operador** - Lista de clientes com busca e filtros
6. **Notificações** - Push + popup in-app
7. **Comandos "/"** - Implementar comandos rápidos
8. **Gestos** - Swipe gestures nas mensagens
9. **Deeplinks** - Navegação interna via links

## 📝 Notas Técnicas

- Usar Room para cache local
- Usar Firebase Firestore para dados em tempo real (ou mock API)
- Usar Firebase Messaging para push notifications
- Implementar ViewModel para cada tela principal
- Usar StateFlow/Flow para reatividade
- Implementar repositórios para abstrair fontes de dados

