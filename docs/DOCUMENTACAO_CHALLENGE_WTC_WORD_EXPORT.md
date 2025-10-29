# WTC CRM Mobile App — Documentação (Export para Word)

Observação: Este arquivo foi formatado para importação direta no Microsoft Word (Office 365). Após importar, insira as imagens das telas onde indicado.

## Capa
- Título: WTC CRM Mobile App — Sprint 1
- Autor: Antonio Sergio Rodrigues Figueiredo (RM56118)
- Data: 29/10/2025
- Plataforma: Android (Kotlin + Jetpack Compose)

## 1. Objetivo do Aplicativo
Descrever os objetivos do WTC CRM Mobile App, incluindo comunicação integrada, CRM e campanhas.

## 2. Stack Tecnológico
- Kotlin 1.9.22, Jetpack Compose, MVVM, Hilt, Room, DataStore
- Firebase (Auth pronto; Firestore integrado; Messaging configurado)
- Gradle 8.5, KSP, Java 21

## 3. Funcionalidades Implementadas
- Autenticação (operador/cliente) com sessão persistente
- Chat 1:1 e por grupo/segmento (comandos rápidos e gestos)
- CRM: lista, busca, filtros, anotações rápidas
- Campanhas Express: criação, segmentação e envio imediato
- Integração com Firestore (users, messages, clients, campaigns)

## 4. Telas (inserir screenshots)

### 4.1 Login
- Descrição breve e elementos principais
[Inserir imagem da tela de Login]

### 4.2 Home
- Acesso rápido aos módulos
[Inserir imagem da tela Home]

### 4.3 Conversas e Chat
- Lista de conversas, envio de mensagens, comandos e gestos
[Inserir imagem das telas de Conversas e Chat]

### 4.4 Clientes (CRM)
- Lista, busca, filtros, anotações rápidas
[Inserir imagem da tela de Clientes]

### 4.5 Campanhas
- Lista e botão “Nova Campanha”
[Inserir imagem da tela de Campanhas]

### 4.6 Criar Campanha
- Formulário, segmento, envio imediato
[Inserir imagem da tela de Criar Campanha]

### 4.7 Perfil
- Dados do usuário e logout
[Inserir imagem da tela de Perfil]

## 5. Serviços e Integrações
- Google Services Plugin 4.4.4; BoM 32.3.1
- Firestore integrado (users, messages, clients, campaigns)
- Sincronização híbrida: Room (cache) + Firestore (nuvem)

## 6. Fluxos Principais
- Login → Home
- Envio de Mensagem → Firestore/Room
- Criar Campanha → salvar/sincronizar e enviar

## 7. Testes e Dados
- Referência: docs/TESTE_FIRESTORE.md (guia de testes)
- Regras: docs/REGRAS_FIRESTORE_PRODUCAO.md

## 8. Publicação para Banca (Azure)
- Azure DevOps Artifact (APK)
- App Center (distribuição para testers)
- Referência: docs/DEPLOY_AZURE.md

## 9. Próximos Passos
- Habilitar Firebase Auth de login
- Listeners em tempo real de Firestore
- Push notifications fim-a-fim

## 10. Conclusão
Resumo do valor entregue na Sprint 1 e caminho para evolução.
