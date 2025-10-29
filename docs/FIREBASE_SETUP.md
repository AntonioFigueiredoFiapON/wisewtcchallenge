# Configuração do Firebase - Challenge WTC CRM

Este guia explica como criar uma conta Firebase e integrar com a aplicação Challenge WTC CRM.

## 📋 Pré-requisitos

- Conta Google (Gmail)
- Acesso à internet
- Android Studio instalado

## 🔥 Passo a Passo: Criar Projeto Firebase

### 1. Acessar Firebase Console

1. Acesse: https://console.firebase.google.com/
2. Faça login com sua conta Google

### 2. Criar Novo Projeto

1. Clique em **"Adicionar projeto"** ou **"Create a project"**
2. **Nome do projeto:** Digite `wtc-crm-app` (ou outro nome de sua preferência)
3. Clique em **"Continuar"**
4. **Google Analytics:** 
   - Pode habilitar (recomendado para produção)
   - Ou desabilitar (para desenvolvimento/teste)
5. Se habilitar Analytics, selecione ou crie uma conta do Google Analytics
6. Clique em **"Criar projeto"**
7. Aguarde a criação do projeto (alguns segundos)

### 3. Adicionar Aplicativo Android

1. No dashboard do projeto, clique no ícone do **Android** (ou "Adicionar app")
2. Preencha:
   - **Nome do pacote Android:** `com.wtc.crm`
   - **Apelido do app:** `WTC CRM App` (opcional)
   - **Certificado de depuração SHA-1:** (opcional por enquanto)
3. Clique em **"Registrar app"**

### 4. Baixar arquivo google-services.json

1. Baixe o arquivo `google-services.json`
2. **IMPORTANTE:** Copie este arquivo para:
   ```
   ChallengeWTCEtapa1/app/google-services.json
   ```
   ⚠️ **Não commit este arquivo no repositório!** Ele contém credenciais sensíveis.

### 5. Habilitar Serviços Firebase

No console Firebase, habilite os seguintes serviços:

#### Firebase Authentication
1. Vá em **Authentication** (Autenticação)
2. Clique em **"Começar"**
3. Habilite **Email/Senha**
4. Opcional: Habilite outros métodos (Google, etc)

#### Cloud Firestore
1. Vá em **Firestore Database**
2. Clique em **"Criar banco de dados"**
3. Selecione modo de segurança:
   - **Modo de teste** (para desenvolvimento) - permite leitura/escrita por 30 dias
   - **Modo de produção** (para produção) - requer regras de segurança
4. Escolha a localização do banco (ex: `southamerica-east1`)
5. Clique em **"Ativar"**

#### Cloud Messaging (FCM)
1. Vá em **Cloud Messaging**
2. O FCM já está disponível automaticamente
3. Para obter a chave do servidor:
   - Vá em **Configurações do projeto** (ícone de engrenagem)
   - Aba **"Cloud Messaging"**
   - Copie a **"Chave do servidor"** (será usada no backend)

## 📱 Verificar Integração no App

### Verificar dependências no build.gradle

O arquivo `app/build.gradle` já possui as dependências necessárias:

```gradle
// Firebase BOM (gerencia versões)
implementation 'com.google.firebase:firebase-bom:32.3.1'

// Serviços específicos
implementation 'com.google.firebase:firebase-messaging:23.2.1'
implementation 'com.google.firebase:firebase-firestore:24.7.1'
implementation 'com.google.firebase:firebase-auth:22.1.2'
implementation 'com.google.firebase:firebase-analytics:21.3.0'
```

### Verificar plugin no build.gradle

O arquivo `app/build.gradle` já possui o plugin:

```gradle
plugins {
    id 'com.google.gms.google-services'
}
```

E o arquivo raiz `build.gradle` possui o classpath:

```gradle
classpath 'com.google.gms:google-services:4.3.15'
```

## 🔐 Configurar Regras de Segurança (Firestore)

Para desenvolvimento, você pode usar regras temporárias:

1. Vá em **Firestore Database** → **Regras**
2. Cole estas regras (APENAS PARA DESENVOLVIMENTO):

```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    // Permite leitura/escrita para qualquer usuário autenticado
    match /{document=**} {
      allow read, write: if request.auth != null;
    }
  }
}
```

3. Clique em **"Publicar"**

⚠️ **ATENÇÃO:** Para produção, use regras mais restritivas baseadas em roles (operador/cliente).

## 📊 Estrutura de Dados Firestore

Recomendamos a seguinte estrutura de coleções:

```
Firestore
├── users/
│   ├── {userId}/
│   │   ├── email: string
│   │   ├── name: string
│   │   ├── role: "OPERATOR" | "CLIENT"
│   │   └── tags: array
│
├── clients/
│   ├── {clientId}/
│   │   ├── name: string
│   │   ├── email: string
│   │   ├── score: number
│   │   ├── status: string
│   │   └── tags: array
│
├── messages/
│   ├── {messageId}/
│   │   ├── type: string
│   │   ├── title: string
│   │   ├── body: string
│   │   ├── senderId: string
│   │   ├── receiverId: string
│   │   ├── timestamp: timestamp
│   │   └── actions: array
│
└── campaigns/
    ├── {campaignId}/
    │   ├── title: string
    │   ├── body: string
    │   ├── segmentType: string
    │   ├── scheduledAt: timestamp
    │   └── sentAt: timestamp
```

## ✅ Verificar Se Está Funcionando

### 1. Build do Projeto

```bash
./gradlew build
```

Se não houver erros relacionados ao Firebase, a integração está correta.

### 2. Testar no App

1. Execute o app no emulador/dispositivo
2. Verifique os logs no Logcat para mensagens do Firebase
3. Tente fazer login (se Firebase Auth estiver configurado)

### 3. Verificar Push Notifications

Para testar push notifications:

1. No Firebase Console, vá em **Cloud Messaging**
2. Clique em **"Enviar sua primeira mensagem"**
3. Preencha título e texto
4. Selecione o app Android
5. Envie
6. Verifique se a notificação chega no dispositivo

## 🔑 Obter Chaves e Configurações

### Server Key (FCM)
1. **Configurações do projeto** → **Cloud Messaging**
2. Copie a **"Chave do servidor"**

### Web API Key
1. **Configurações do projeto** → **Geral**
2. Na seção **"Seus apps"**, encontre o app Android
3. Copie a **"Chave de API da Web"**

## 📝 Exemplo de google-services.json

O arquivo deve ter estrutura similar a:

```json
{
  "project_info": {
    "project_number": "123456789012",
    "project_id": "wtc-crm-app",
    "storage_bucket": "wtc-crm-app.appspot.com"
  },
  "client": [
    {
      "client_info": {
        "mobilesdk_app_id": "1:123456789012:android:abcdef123456",
        "android_client_info": {
          "package_name": "com.wtc.crm"
        }
      },
      "oauth_client": [],
      "api_key": [
        {
          "current_key": "AIzaSyXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
        }
      ],
      "services": {
        "appinvite_service": {
          "other_platform_oauth_client": []
        }
      }
    }
  ],
  "configuration_version": "1"
}
```

## 🚨 Troubleshooting

### Erro: "File google-services.json is missing"
- Verifique se o arquivo está em `app/google-services.json`
- Verifique se o nome do pacote é `com.wtc.crm`

### Erro: "SHA-1 certificate fingerprint"
- Para FCM, geralmente não é necessário em desenvolvimento
- Para produção, obtenha o SHA-1 com:
  ```bash
  keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android
  ```

### Erro ao conectar Firestore
- Verifique se o Firestore está criado no console
- Verifique as regras de segurança
- Verifique a localização do banco

## 📚 Recursos Adicionais

- [Documentação Firebase Android](https://firebase.google.com/docs/android/setup)
- [Firebase Auth](https://firebase.google.com/docs/auth/android/start)
- [Cloud Firestore](https://firebase.google.com/docs/firestore/quickstart)
- [Cloud Messaging](https://firebase.google.com/docs/cloud-messaging/android/client)

---

**Próximos Passos:**
- Configure as regras de segurança apropriadas
- Implemente autenticação no app
- Configure grupos de mensagens
- Configure segmentação de campanhas

