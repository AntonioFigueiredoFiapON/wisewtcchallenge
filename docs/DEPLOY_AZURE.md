# Publicação no Azure (Azure DevOps e App Center)

Este guia descreve como disponibilizar o app para a banca via Azure, mantendo padrão profissional e sem depender de lojas públicas.

## Opção A — Azure DevOps: Pipeline + Artifact de APK

1. Build local do APK
```bash
./gradlew :app:assembleDebug
# APK gerado em: app/build/outputs/apk/debug/app-debug.apk
```
2. Commit e push para Azure DevOps (repositório já configurado)
3. Crie um Pipeline YAML simples (ou clássico) que:
   - Executa `./gradlew :app:assembleDebug`
   - Publica `app/build/outputs/apk/**` como Artifact
4. Compartilhe o link do Artifact com a banca (download direto do Azure DevOps)

Vantagens:
- Simples, sem custos adicionais
- Link privado de download do APK

## Opção B — Microsoft App Center (Distribuição para Testers)

1. Crie uma conta no App Center (`appcenter.ms`)
2. Crie um app Android (pacote `com.wtc.crm`)
3. Faça upload do APK (`app-debug.apk`) manualmente ou via CLI
4. Crie um grupo de distribuição (ex.: "Banca-Professores")
5. Envie convites por e-mail; os convidados recebem link para instalar

Vantagens:
- Experiência profissional de distribuição
- Estatísticas básicas de instalação

## Opção C — Hospedar Documentação e APK em Azure Static Web Apps

1. Crie um projeto Static Web Apps
2. Publique a pasta `docs/` (inclua um link para o APK hospedado em Artifact ou App Center)
3. Entregue um link único com documentação + botão "Baixar APK"

## Observações Importantes

- Apps Android não rodam "online" em um navegador. A banca deve instalar em um dispositivo Android ou usar emulador.
- Para demonstração remota, use: 
  - Emulador Android no notebook com screen share; ou
  - App Center (instalação direta em dispositivos da banca).

## Exemplo de Pipeline YAML (Azure DevOps)

```yaml
trigger:
  - main

pool:
  vmImage: 'macos-latest'

steps:
  - task: Gradle@3
    inputs:
      gradleWrapperFile: 'gradlew'
      tasks: 'clean :app:assembleDebug'
      publishJUnitResults: false

  - task: PublishBuildArtifacts@1
    inputs:
      PathtoPublish: 'app/build/outputs/apk/'
      ArtifactName: 'apk'
      publishLocation: 'Container'
```

## Checklist para Entrega

- [ ] APK publicado (Artifact ou App Center)
- [ ] Documentação atualizada (`DOCUMENTACAO_CHALLENGE_WTC.md`)
- [ ] Guia de testes (`docs/TESTE_FIRESTORE.md`)
- [ ] Regras Firestore de produção planejadas (`docs/REGRAS_FIRESTORE_PRODUCAO.md`)
- [ ] Instruções de instalação para a banca (Android 8+)
