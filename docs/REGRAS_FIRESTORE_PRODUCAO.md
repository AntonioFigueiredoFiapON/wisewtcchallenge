# 🔒 Regras de Segurança Firestore - Produção

## ⚠️ Importante

As regras atuais estão em **Test Mode** e expiram em **28 de novembro de 2025**.

Configure as regras de produção **antes dessa data** para garantir segurança adequada.

## 📋 Regras Recomendadas para Produção

### Versão Completa (Recomendada)

```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    
    // ========== USERS ==========
    // Usuários podem ler seus próprios dados
    // Apenas operadores podem criar/atualizar usuários
    match /users/{userId} {
      allow read: if request.auth != null && 
                     (request.auth.uid == userId || 
                      get(/databases/$(database)/documents/users/$(request.auth.uid)).data.role == 'OPERATOR');
      
      allow create: if request.auth != null && 
                       request.auth.uid == userId;
      
      allow update, delete: if request.auth != null && 
                               (request.auth.uid == userId || 
                                get(/databases/$(database)/documents/users/$(request.auth.uid)).data.role == 'OPERATOR');
    }
    
    // ========== MESSAGES ==========
    // Mensagens: apenas sender e receiver podem ler
    // Qualquer usuário autenticado pode criar mensagens
    match /messages/{messageId} {
      allow read: if request.auth != null && 
                     (resource.data.fromUserId == request.auth.uid || 
                      resource.data.toUserId == request.auth.uid ||
                      resource.data.groupId != null);
      
      allow create: if request.auth != null && 
                       request.auth.uid == request.resource.data.fromUserId;
      
      allow update: if request.auth != null && 
                        (request.auth.uid == resource.data.fromUserId ||
                         request.auth.uid == resource.data.toUserId);
      
      allow delete: if request.auth != null && 
                       request.auth.uid == resource.data.fromUserId;
    }
    
    // ========== CLIENTS ==========
    // Clientes: apenas operadores podem acessar
    match /clients/{clientId} {
      allow read: if request.auth != null && 
                     get(/databases/$(database)/documents/users/$(request.auth.uid)).data.role == 'OPERATOR';
      
      allow write: if request.auth != null && 
                      get(/databases/$(database)/documents/users/$(request.auth.uid)).data.role == 'OPERATOR';
    }
    
    // ========== CAMPAIGNS ==========
    // Campanhas: apenas operadores podem criar/editar
    // Clientes podem ler campanhas enviadas para eles
    match /campaigns/{campaignId} {
      allow read: if request.auth != null;
      
      allow write: if request.auth != null && 
                      get(/databases/$(database)/documents/users/$(request.auth.uid)).data.role == 'OPERATOR';
    }
  }
}
```

### Versão Simplificada (Para Desenvolvimento Avançado)

```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    
    // Usuários autenticados podem ler/escrever tudo
    // ⚠️ Apenas para desenvolvimento/testes
    match /{document=**} {
      allow read, write: if request.auth != null;
    }
  }
}
```

### Versão Intermediária (Balanceada)

```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    
    // Usuários: acesso próprio + operadores
    match /users/{userId} {
      allow read: if request.auth != null;
      allow write: if request.auth != null;
    }
    
    // Mensagens: sender e receiver
    match /messages/{messageId} {
      allow read, write: if request.auth != null &&
        (resource.data.fromUserId == request.auth.uid || 
         resource.data.toUserId == request.auth.uid);
    }
    
    // Clientes e Campanhas: autenticados
    match /{collection}/{document} {
      allow read, write: if request.auth != null && 
                           (collection == 'clients' || 
                            collection == 'campaigns' ||
                            collection == 'users');
    }
  }
}
```

## 🔧 Como Aplicar as Regras

### Via Firebase Console

1. Acesse: https://console.firebase.google.com/project/wisecomwtccrm/firestore/rules
2. Cole a regra escolhida acima
3. Clique em **Publicar**
4. Aguarde a confirmação

### Via Firebase CLI (Opcional)

```bash
# Instalar Firebase CLI (se ainda não tiver)
npm install -g firebase-tools

# Login
firebase login

# Aplicar regras
firebase deploy --only firestore:rules
```

## 🧪 Como Testar as Regras

### No Firebase Console

1. Vá em **Firestore Database** → **Regras**
2. Clique em **Simulador de regras**
3. Teste cenários:
   - Usuário tentando ler próprio perfil
   - Operador tentando criar cliente
   - Cliente tentando ler mensagens

### No App

1. Teste login de diferentes tipos de usuário
2. Verifique permissões de leitura/escrita
3. Monitore erros no Logcat

## 📝 Notas de Segurança

### Boas Práticas

1. **Nunca exponha todas as coleções** sem autenticação
2. **Use roles** (OPERATOR/CLIENT) para controlar acesso
3. **Valide dados** antes de permitir escrita
4. **Limite leituras** apenas aos dados necessários
5. **Use índices** para queries complexas

### Validação de Dados

As regras podem validar:

```javascript
// Exemplo: Validar estrutura de mensagem
allow create: if request.auth != null &&
                 request.resource.data.keys().hasAll(['id', 'fromUserId', 'body']) &&
                 request.resource.data.id is string &&
                 request.resource.data.fromUserId == request.auth.uid;
```

## 🚨 Alertas Importantes

1. **Test Mode expira em 28/11/2025**
   - Configure regras de produção antes dessa data
   - Teste as regras antes de publicar

2. **Não use regras permissivas em produção**
   - Evite `allow read, write: if true` em produção
   - Sempre exija autenticação

3. **Monitore uso no Console**
   - Verifique métricas de acesso
   - Analise padrões de uso

## 📚 Referências

- [Documentação Oficial Firestore Rules](https://firebase.google.com/docs/firestore/security/get-started)
- [Simulador de Regras](https://firebase.google.com/docs/firestore/security/test-rules-emulator)
- [Melhores Práticas](https://firebase.google.com/docs/firestore/security/rules-conditions)

---

**Status Atual:** Test Mode (válido até 28/11/2025)  
**Próxima Ação:** Configurar regras de produção  
**Recomendação:** Usar versão "Completa" acima para produção

