# Challenge WTC - CRM Mobile App

## 📱 Sobre o Projeto

Aplicativo móvel nativo Android desenvolvido para o Challenge WTC, focado em integração de comunicação entre operadores e clientes através de um CRM mobile completo.

## 🎯 Sprint 1 - Funcionalidades

### ✅ Implementado

- **Estrutura Base**
  - Projeto configurado com Kotlin 1.9.22, Java 21, KSP
  - Arquitetura MVVM com Hilt (Dependency Injection)
  - Room Database para cache local
  - Jetpack Compose para UI
  - Firebase integrado (Auth, Firestore, Messaging)

- **Telas Principais**
  - Login (Cliente/Operador)
  - Home com ações rápidas
  - Conversas (Chat 1:1 e grupos)
  - Clientes (Lista, busca, filtros)
  - Campanhas (Lista e criação)
  - Perfil

- **Navegação**
  - Navigation Compose
  - Bottom Navigation Bar
  - Deeplinks configurados (`wtc://crm/*`)

### 🚧 Em Desenvolvimento

- **Autenticação Completa**
  - Login mockado → migrar para Firebase Auth
  - Persistência de sessão
  - Diferenciación Operador/Cliente

- **Chat Funcional**
  - Mensagens mockadas → integrar com Firebase Firestore
  - Push notifications (Firebase Messaging configurado)
  - Popup in-app para novas mensagens
  - Modelo flexível de mensagem com actions

- **CRM Operador**
  - Lista mockada → integrar com dados reais
  - Busca e filtros funcionais
  - Anotações rápidas por cliente
  - Detalhes do cliente

- **Campanhas Express**
  - Lista mockada → integrar com Firebase
  - Criação de campanha express
  - Envio para segmentos
  - Modelo flexível de mensagem implementado

### ❌ A Implementar

- **Comandos Rápidos "/"**
  - `/promo` - Criar promoção rápida
  - `/boleto` - Enviar lembrete de boleto
  - `/agradecer` - Mensagem de agradecimento

- **Gestos Inteligentes**
  - Swipe direito → Marcar como importante
  - Swipe esquerdo → Criar tarefa
  - Long press → Menu de ações

- **Deeplinks Funcionais**
  - `wtc://crm/client/{id}` - Abrir perfil do cliente
  - `wtc://crm/campaign/{id}` - Abrir campanha
  - `wtc://crm/purchase/{id}` - Última compra

## 🏗️ Arquitetura

### Stack Tecnológico

- **Linguagem:** Kotlin 1.9.22
- **JDK:** Java 21
- **Min SDK:** 24 (Android 7.0)
- **Target SDK:** 34 (Android 14)
- **Build System:** Gradle 8.5
- **Plugin:** Android Gradle Plugin 8.2.2

### Bibliotecas Principais

- **UI:** Jetpack Compose 2024.01.00
- **DI:** Hilt 2.51.1 (com KSP)
- **Database:** Room 2.6.1
- **Networking:** Retrofit 2.9.0, OkHttp 4.11.0
- **Firebase:** 
  - Auth 22.1.2
  - Firestore 24.7.1
  - Messaging 23.2.1
  - Analytics 21.3.0
- **Navigation:** Navigation Compose 2.7.0
- **Imagens:** Coil 2.4.0
- **Animações:** Lottie 6.0.1

### Estrutura de Pacotes

```
com.wtc.crm/
├── data/              # Models, DAOs, Database
├── di/                # Dependency Injection modules
├── messaging/         # Firebase Messaging
├── ui/
│   ├── navigation/   # Navigation e rotas
│   ├── screens/      # Telas Compose
│   └── theme/        # Tema e cores
└── WTCApplication.kt  # Application class
```

## 🚀 Como Executar

### Pré-requisitos

- Android Studio Hedgehog ou superior
- JDK 21
- Android SDK 34
- Emulador Android ou dispositivo físico

### Configuração

1. Clone o repositório
2. Configure Firebase:
   - Adicione `google-services.json` em `app/`
   - Veja `docs/FIREBASE_SETUP.md` para detalhes
3. Sincronize o projeto no Android Studio
4. Execute o app

### Build

```bash
./gradlew assembleDebug
```

O APK será gerado em `app/build/outputs/apk/debug/app-debug.apk`

## 📋 Modelo de Mensagem Flexível

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

## 🔍 Próximos Passos

1. Corrigir crash na inicialização
2. Implementar autenticação completa com Firebase
3. Integrar chat com Firestore em tempo real
4. Implementar comandos rápidos "/"
5. Adicionar gestos inteligentes
6. Conectar deeplinks funcionais
7. Adicionar testes unitários e de integração

## 📄 Documentação

- `docs/ARQUITETURA.md` - Detalhes da arquitetura MVVM
- `docs/FIREBASE_SETUP.md` - Guia de configuração Firebase
- `SPRINT1_PLANO_ACAO.md` - Plano de ação Sprint 1
- `Challenge - WTC/` - Documentos do desafio

## 👥 Equipe

Desenvolvido para o Challenge WTC - Sprint 1

## 📝 Licença

Este projeto é parte do Challenge WTC.
