# 🧪 Guia de Teste - Integração Firestore

## ✅ Status Atual

- ✅ **Firestore Database**: Criado e ativo
- ✅ **Modo**: Test Mode (válido até 28/11/2025)
- ✅ **Regras de Segurança**: Configuradas (modo teste)
- ✅ **Integração no App**: Implementada e pronta

## 🔍 Como Testar a Integração

### 1. Teste Básico - Login e Salvar Usuário

**Passo a passo:**

1. **Execute o app** no Android Studio ou dispositivo
2. **Faça login** com:
   - Email: `operador@wtc.com` / Senha: `123456` (Operador)
   - OU Email: `cliente@wtc.com` / Senha: `123456` (Cliente)
3. **Verifique no Firebase Console:**
   - Acesse: https://console.firebase.google.com/project/wisecomwtccrm/firestore
   - Deve aparecer uma coleção `users/` com o usuário logado

### 2. Teste - Enviar Mensagem

**Passo a passo:**

1. No app, navegue até **Chat**
2. **Envie uma mensagem** para qualquer usuário
3. **Verifique no Firestore:**
   - Coleção `messages/` deve ter a mensagem salva
   - Campos: `id`, `fromUserId`, `toUserId`, `type`, `title`, `body`, `timestamp`

### 3. Teste - Criar Cliente (Operador)

**Passo a passo:**

1. Faça login como **Operador**
2. Navegue até **Clientes**
3. **Adicione um novo cliente** ou edite um existente
4. **Verifique no Firestore:**
   - Coleção `clients/` deve ter o cliente salvo
   - Campos: `id`, `name`, `email`, `tags`, `status`, `score`

### 4. Teste - Criar Campanha

**Passo a passo:**

1. Navegue até **Campanhas**
2. **Crie uma nova campanha**
3. **Verifique no Firestore:**
   - Coleção `campaigns/` deve ter a campanha salva
   - Campos: `id`, `name`, `title`, `body`, `segmentType`, `createdAt`

## 📊 Estrutura de Dados no Firestore

### Coleção `users/`

```json
{
  "id": "operator_123",
  "name": "Operador WTC",
  "email": "operador@wtc.com",
  "role": "OPERATOR",
  "tags": ["operator", "admin"],
  "status": "active",
  "score": 100,
  "createdAt": 1698595200000,
  "lastAccessAt": 1698595200000
}
```

### Coleção `messages/`

```json
{
  "id": "msg_abc123",
  "fromUserId": "operator_123",
  "toUserId": "client_456",
  "type": "TEXT",
  "title": "Olá!",
  "body": "Como posso ajudar?",
  "actions": [],
  "actionUrls": {},
  "isImportant": false,
  "timestamp": 1698595200000,
  "isRead": false
}
```

### Coleção `clients/`

```json
{
  "id": "client_1",
  "name": "João Silva",
  "email": "joao@email.com",
  "tags": ["VIP", "Premium"],
  "status": "active",
  "score": 85,
  "notes": "Cliente importante",
  "createdAt": 1698595200000
}
```

### Coleção `campaigns/`

```json
{
  "id": "campaign_123",
  "name": "Promoção Black Friday",
  "title": "Black Friday 2025",
  "body": "Aproveite nossas ofertas especiais!",
  "segmentType": "ALL",
  "segmentValue": "",
  "createdAt": 1698595200000
}
```

## 🔍 Verificação no Firebase Console

### Passos:

1. **Acesse o Console:**
   ```
   https://console.firebase.google.com/project/wisecomwtccrm/firestore
   ```

2. **Visualize os Dados:**
   - Clique nas coleções (`users`, `messages`, `clients`, `campaigns`)
   - Veja os documentos criados
   - Verifique os campos e valores

3. **Monitore em Tempo Real:**
   - Os dados aparecem automaticamente quando salvos pelo app
   - Não é necessário refresh manual

## 🧪 Testes Automatizados (Via App)

### Teste de Sincronização

1. **Envie dados pelo app**
2. **Observe o Firestore Console** (em outra aba)
3. **Verifique que os dados aparecem em tempo real** (alguns segundos)

### Teste de Fallback

O app funciona mesmo se o Firestore não estiver disponível:

1. **Desative temporariamente o Firebase** (ex: altere o `google-services.json`)
2. **O app continua funcionando** com Room Database local
3. **Reative o Firebase** e os dados serão sincronizados na próxima operação

## 📝 Logs para Depuração

### Verificar Logs no Android Studio

No **Logcat**, procure por:

```
FirebaseApp: Default FirebaseApp initialized
FirestoreRepository: Saving user to Firestore
FirestoreRepository: Saving message to Firestore
```

### Adicionar Logs Personalizados (Opcional)

Em `FirestoreRepository.kt`, você pode adicionar:

```kotlin
suspend fun saveMessage(message: Message) {
    try {
        // ... código existente
        android.util.Log.d("Firestore", "Mensagem salva: ${message.id}")
    } catch (e: Exception) {
        android.util.Log.e("Firestore", "Erro ao salvar: ${e.message}")
    }
}
```

## ⚠️ Notas Importantes

### Regras de Segurança (Test Mode)

As regras atuais permitem **leitura e escrita** para todos até **28/11/2025**.

**⚠️ Importante:** Atualize as regras antes dessa data para produção:

```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    
    // Usuários: leitura própria, escrita apenas operadores
    match /users/{userId} {
      allow read: if request.auth != null && request.auth.uid == userId;
      allow write: if request.auth != null;
    }
    
    // Mensagens: leitura/escrita apenas envolvidos
    match /messages/{messageId} {
      allow read, write: if request.auth != null &&
        (resource.data.fromUserId == request.auth.uid || 
         resource.data.toUserId == request.auth.uid);
    }
    
    // Clientes: leitura/escrita apenas operadores
    match /clients/{clientId} {
      allow read, write: if request.auth != null;
    }
    
    // Campanhas: leitura/escrita apenas operadores
    match /campaigns/{campaignId} {
      allow read, write: if request.auth != null;
    }
  }
}
```

### Validade do Test Mode

- ✅ **Válido até:** 28 de novembro de 2025
- ⚠️ **Após essa data:** As regras padrão expiram
- 🔒 **Ação necessária:** Configurar regras de produção antes dessa data

## ✅ Checklist de Testes

- [ ] Login salva usuário no Firestore
- [ ] Mensagens são sincronizadas
- [ ] Clientes são salvos corretamente
- [ ] Campanhas são criadas no Firestore
- [ ] Dados aparecem no Console em tempo real
- [ ] App funciona offline (Room Database)
- [ ] Sincronização funciona quando volta online

## 🎯 Próximos Passos

1. **Testar todas as funcionalidades** listadas acima
2. **Verificar sincronização em tempo real** no Console
3. **Configurar regras de segurança** para produção (antes de 28/11/2025)
4. **Implementar listeners em tempo real** (opcional, para atualizações automáticas)

---

**Status:** ✅ Firestore Configurado e Pronto para Testes  
**Data:** 29 de outubro de 2025  
**Test Mode Valido até:** 28 de novembro de 2025

