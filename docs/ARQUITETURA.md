# Arquitetura do Sistema - Challenge WTC CRM

## 📐 Visão Geral

O aplicativo **Challenge WTC CRM** segue uma arquitetura **MVVM (Model-View-ViewModel)** com padrões de design modernos para Android.

## 🏗️ Camadas da Arquitetura

```
┌─────────────────────────────────────────┐
│         UI Layer (Jetpack Compose)      │
│  ┌──────────┐  ┌──────────┐           │
│  │ Screens  │  │ Navigation│           │
│  └──────────┘  └──────────┘           │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│      Presentation Layer (ViewModels)     │
│  ┌──────────────────────────────────┐   │
│  │ ViewModels (Livedata/StateFlow) │   │
│  └──────────────────────────────────┘   │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│         Domain Layer (Business Logic)   │
│  ┌──────────────────────────────────┐   │
│  │ Repositories (Use Cases)         │   │
│  └──────────────────────────────────┘   │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│         Data Layer                      │
│  ┌──────────┐  ┌──────────┐           │
│  │   Room   │  │ Firebase │           │
│  │ Database │  │ Services │           │
│  └──────────┘  └──────────┘           │
└─────────────────────────────────────────┘
```

## 📦 Estrutura de Pacotes

```
com.wtc.crm/
│
├── data/                          # Camada de Dados
│   ├── User.kt                    # Entity - Usuário
│   ├── Client.kt                  # Entity - Cliente CRM
│   ├── Message.kt                 # Entity - Mensagem
│   ├── Campaign.kt                # Entity - Campanha
│   ├── QuickNote.kt               # Entity - Nota Rápida
│   │
│   ├── UserDao.kt                 # DAO - Acesso a dados User
│   ├── ClientDao.kt               # DAO - Acesso a dados Client
│   ├── MessageDao.kt              # DAO - Acesso a dados Message
│   ├── CampaignDao.kt             # DAO - Acesso a dados Campaign
│   ├── QuickNoteDao.kt            # DAO - Acesso a dados QuickNote
│   │
│   ├── AppDatabase.kt             # Room Database
│   ├── Converters.kt               # Type Converters (List, Date)
│   │
│   ├── UserRepository.kt          # Repository - User
│   ├── ClientRepository.kt        # Repository - Client
│   ├── MessageRepository.kt      # Repository - Message
│   ├── CampaignRepository.kt      # Repository - Campaign
│   └── QuickNoteRepository.kt     # Repository - QuickNote
│
├── di/                            # Dependency Injection
│   └── DatabaseModule.kt          # Módulos Hilt
│
├── messaging/                     # Serviços de Mensageria
│   └── FCMMessagingService.kt     # Firebase Cloud Messaging
│
├── ui/                            # Camada de Interface
│   ├── navigation/                # Navegação
│   │   ├── Screen.kt              # Definição de rotas
│   │   └── WTCApp.kt              # NavHost principal
│   │
│   ├── screens/                   # Telas (Views)
│   │   ├── LoginScreen.kt
│   │   ├── HomeScreen.kt
│   │   ├── ChatScreen.kt
│   │   ├── ConversationsScreen.kt
│   │   ├── ClientsScreen.kt
│   │   ├── CampaignsScreen.kt
│   │   └── ProfileScreen.kt
│   │
│   ├── theme/                     # Design System
│   │   ├── Color.kt               # Cores do tema
│   │   ├── Theme.kt               # Material 3 Theme
│   │   └── Type.kt                 # Tipografia
│   │
│   └── viewmodel/                 # ViewModels (Preparados)
│       └── (a implementar)
│
├── MainActivity.kt                # Activity Principal
└── WTCApplication.kt              # Application Class (Hilt)
```

## 🔄 Fluxo de Dados

### Autenticação

```
LoginScreen
    ↓ (user action)
LoginViewModel
    ↓ (business logic)
UserRepository
    ↓
Firebase Auth ──────────┐
    │                    │
    └── Room Database    │ (cache local)
                        │
    ┌───────────────────┘
    ↓
LoginScreen (update UI)
```

### Chat/Mensagens

```
ChatScreen
    ↓ (observe)
MessageViewModel
    ↓ (fetch/observe)
MessageRepository
    ↓
Firebase Firestore ─────┐ (real-time)
    │                    │
    └── Room Database    │ (cache)
                        │
    ┌───────────────────┘
    ↓
ChatScreen (update UI com novas mensagens)
```

### CRM - Lista de Clientes

```
ClientsScreen
    ↓ (observe/search/filter)
ClientViewModel
    ↓
ClientRepository
    ↓
Room Database ────┐ (local cache)
    │              │
Firebase Firestore ┘ (sync)
    │
    ↓
ClientsScreen (exibe lista filtrada)
```

## 🗄️ Modelo de Dados

### User (Usuário)

```kotlin
@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: String,
    val name: String,
    val email: String,
    val role: UserRole,           // OPERATOR ou CLIENT
    val tags: List<String>,
    val status: String,
    val score: Int
)
```

### Client (Cliente CRM)

```kotlin
@Entity(tableName = "clients")
data class Client(
    @PrimaryKey val id: String,
    val name: String,
    val email: String,
    val tags: List<String>,
    val status: String,            // ACTIVE, INACTIVE, VIP
    val score: Int,                // 0-100
    val notes: String?
)
```

### Message (Mensagem Rica)

```kotlin
@Entity(tableName = "messages")
data class Message(
    @PrimaryKey val id: String,
    val type: MessageType,         // TEXT, CAMPAIGN, PROMOTION, BANNER, EVENT
    val title: String,
    val body: String,
    val senderId: String,
    val receiverId: String,
    val url: String?,
    val actions: List<MessageAction>,
    val actionUrls: Map<String, String>,
    val isImportant: Boolean,
    val isRead: Boolean,
    val timestamp: Date
)
```

### Campaign (Campanha)

```kotlin
@Entity(tableName = "campaigns")
data class Campaign(
    @PrimaryKey val id: String,
    val title: String,
    val body: String,
    val actions: List<MessageAction>,
    val segmentType: SegmentType,  // ALL, GROUP, INDIVIDUAL, TAGS
    val segmentValue: String?,     // ID do grupo, cliente ou tags
    val scheduledAt: Date?,
    val sentAt: Date?
)
```

## 🔌 Integração com Serviços

### Firebase Services

#### 1. Firebase Authentication
- **Uso:** Autenticação de usuários (operadores e clientes)
- **Localização:** `UserRepository`

#### 2. Cloud Firestore
- **Uso:** Banco de dados em tempo real
- **Estrutura:** Coleções `users`, `clients`, `messages`, `campaigns`
- **Sync:** Sincronização bidirecional com Room Database

#### 3. Cloud Messaging (FCM)
- **Uso:** Push notifications e mensagens in-app
- **Localização:** `FCMMessagingService.kt`
- **Features:** Notificações de novas mensagens, campanhas

### Room Database
- **Uso:** Cache local e persistência offline
- **Localização:** `AppDatabase.kt`
- **Entities:** User, Client, Message, Campaign, QuickNote

## 🎯 Padrões de Design

### Repository Pattern
- Abstrai a fonte de dados (Firebase/Room)
- Única fonte de verdade
- Facilita testes e manutenção

```kotlin
interface UserRepository {
    suspend fun getUser(id: String): User?
    suspend fun login(email: String, password: String): Result<User>
    fun observeUser(id: String): Flow<User?>
}
```

### Dependency Injection (Hilt)
- Injeção automática de dependências
- Facilita testes unitários
- Reduz acoplamento

```kotlin
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(...).build()
    }
}
```

### MVVM Pattern
- **Model:** Entities e Repositories
- **View:** Jetpack Compose Screens
- **ViewModel:** Gerencia estado e lógica de apresentação

## 🔐 Segurança

### Regras de Segurança Firestore

```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    // Usuários: leitura própria, escrita apenas operadores
    match /users/{userId} {
      allow read: if request.auth != null && request.auth.uid == userId;
      allow write: if request.auth != null && 
                     get(/databases/$(database)/documents/users/$(request.auth.uid)).data.role == 'OPERATOR';
    }
    
    // Mensagens: leitura/escrita apenas envolvidos
    match /messages/{messageId} {
      allow read: if request.auth != null && 
                     (resource.data.senderId == request.auth.uid || 
                      resource.data.receiverId == request.auth.uid);
      allow write: if request.auth != null;
    }
    
    // Clientes: leitura operadores, escrita apenas operadores
    match /clients/{clientId} {
      allow read: if request.auth != null && 
                     get(/databases/$(database)/documents/users/$(request.auth.uid)).data.role == 'OPERATOR';
      allow write: if request.auth != null && 
                     get(/databases/$(database)/documents/users/$(request.auth.uid)).data.role == 'OPERATOR';
    }
  }
}
```

## 🚀 Fluxos Principais

### 1. Login de Usuário

```
1. Usuário preenche email/senha
2. LoginScreen → LoginViewModel.login()
3. LoginViewModel → UserRepository.authenticate()
4. UserRepository → Firebase Auth
5. Sucesso → Salva no Room Database
6. Navegação para HomeScreen
```

### 2. Envio de Mensagem

```
1. Usuário digita mensagem
2. ChatScreen → ChatViewModel.sendMessage()
3. ChatViewModel → MessageRepository.save()
4. MessageRepository → Firebase Firestore
5. Firestore → Room Database (cache)
6. Firestore → FCM (notificação push)
7. UI atualiza automaticamente
```

### 3. Criação de Campanha

```
1. Operador preenche campanha
2. CampaignsScreen → CampaignViewModel.create()
3. CampaignViewModel → CampaignRepository.save()
4. CampaignRepository → Firebase Firestore
5. Sistema → Envia para segmentos
6. FCM → Push notifications para clientes
```

## 📱 Navegação

### Rotas Definidas

```kotlin
sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Home : Screen("home")
    object Chat : Screen("chat/{chatId}")
    object Conversations : Screen("conversations")
    object Clients : Screen("clients")
    object Campaigns : Screen("campaigns")
    object Profile : Screen("profile")
}
```

### Deep Links

- **Schema:** `wtc://crm`
- **Uso:** Navegação interna, links externos
- **Exemplo:** `wtc://crm/client/123`

## 🔄 Sincronização de Dados

### Estratégia de Sync

1. **Firebase → Room (Pull):**
   - Ao abrir app, sincroniza dados do Firestore
   - Updates em tempo real via listeners

2. **Room → Firebase (Push):**
   - Ao criar/editar localmente, envia para Firebase
   - Confirma sucesso antes de atualizar localmente

3. **Offline First:**
   - Dados sempre disponíveis no Room
   - Sincronização em background quando online

## 📊 Diagrama de Sequência

### Envio de Mensagem

```
User → ChatScreen → ViewModel → Repository
                                      ↓
                          Firestore ← Room
                                      ↓
                              FCM → Push Notification
                                      ↓
                          Other Devices → Update UI
```

## 🧪 Testabilidade

- **ViewModels:** Testáveis com dados mockados
- **Repositories:** Testáveis com Room in-memory
- **UI:** Testes de composição
- **Integration:** Testes com Firebase emuladores

## 📈 Escalabilidade

- **Modular:** Fácil adicionar novos recursos
- **Cache:** Room Database para performance
- **Real-time:** Firestore para sincronização
- **Offline:** Funciona sem conexão

---

**Próximas Melhorias:**
- Implementar ViewModels completos
- Adicionar sincronização de conflitos
- Implementar testes unitários
- Adicionar analytics

