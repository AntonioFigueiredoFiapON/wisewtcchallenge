WTC busca aprimorar a comunicação e o relacionamento com seus clientes, oferecendo uma experiência semelhante aos aplicativos de mensagens modernos, mas integrada ao seu CRM corporativo.

O objetivo é permitir que operadores de atendimento e times de marketing disparem mensagens segmentadas — sejam elas promoções, campanhas, banners ou convites para eventos — diretamente para grupos ou clientes específicos.

Essas mensagens devem aparecer em notificações pop-up no aplicativo, serem armazenadas em um histórico de chat e trazer opções interativas como botões de ação e links, ampliando o engajamento e criando novos pontos de contato.

 

OBJETIVO:

O desafio consiste em desenvolver uma solução completa composta por:

- Sprint 1 - Aplicativo móvel nativo (Android ou iOS) que permita ao cliente receber mensagens, visualizar campanhas e interagir por meio de botões e links; 

- Sprint 2 - Backend robusto (Java ou C#) capaz de gerenciar clientes, segmentar audiências, orquestrar envios, registrar histórico e integrar-se ao CRM.

As mensagens enviadas pelo sistema devem seguir um modelo rico e flexível, possibilitando personalização de conteúdo e múltiplas opções de interação.

Funcionalidades requeridas

Autenticação
• Login de operador ou cliente.

Chat integrado ao CRM
• Conversas 1:1 e por grupo/segmento.
• Push + popup in-app para novas mensagens.

CRM no App (visão operador)
• Lista de clientes com busca e filtros (tags/score/status).
• Anotações rápidas por cliente.

Campanhas Express
• Envio imediato de promoções ou comunicados para segmentos simples.

Usabilidade Diferenciada.

• Comandos rápidos “/” (ex.: /promo, /boleto, /agradecer).
• Gestos inteligentes (deslizar msg → marcar como importante, criar tarefa).
• Deeplinks internos (abrir perfil, última compra etc.).

Modelo Flexível de Mensagem
Mensagens enviadas podem ser promoções, campanhas, banners ou eventos, com
opções de interação configuráveis:
{
"title": "Campanha Especial",
"body": "Participe do nosso evento exclusivo!",
"url": "https://wtc.com/evento",
"actions": [
{ "action": "btn1", "title": "Inscrever-se" },
{ "action": "btn2", "title": "Saiba Mais" }
],
"actionUrls": {
"btn1": "https://wtc.com/evento/inscricao",
"btn2": "https://wtc.com/evento/detalhes",
"abrir": "https://wtc.com/evento"
}
}

Requisitos Técnicos— Entrega
Plataforma
Aplicativo nativo para Android ou iOS.

Funcionalidades esperadas (todas)
O app deve contemplar todas as funcionalidades principais definidas no desafio.

Conectividade com Serviços Externos (obrigatória)
Pelo menos uma das opções abaixo deve ser implementada de forma efetiva e funcional (não apenas demonstrativa):

o Consumo de APIs REST (mockadas ou reais).

o Integração com serviços como Firebase, OneSignal, Backendless, AWS Amplify.

o Uso de Banco de dados em tempo real ou em nuvem.

Entregas Obrigatórias

o APK (Android) ou IPA (iOS).
o Código-fonte completo compactado (.zip).
o Documentação técnica (.PDF ou .PPT).
Tecnologias utilizadas, arquitetura da solução e fluxo da aplicação.
o Vídeo demonstrativo (máx. 5 minutos).
Principais funcionalidades implementadas.
Critérios de Avaliação
o Adequação ao problema proposto— 25%.
o Implementação técnica funcional— 30%.
o Qualidade do código— 25%.
o Apresentação e documentação— 5%.
o Criatividade e inovação— 15%.


30/09– 19h30 → Kick-off Challenge WTC
30/10– 23h00 → Entrega Sprint 1
Equipe
o As equipes devem ser formadas apenas por alunos da mesma turma. Ex.: todos
2TDSR.
o A entrega na plataforma deve ser feita por um único integrante.
o No momento da entrega será registrada a formação da equipe.
o Não há prazo extra de 3 dias → atenção à data oficial de entrega.
o No Microsoft Teams está disponível o passo a passo para formação da equipe.
o Máximo de 5 alunos por equipe.
o Artefatos da entrega– Valor: 10 pontos.



