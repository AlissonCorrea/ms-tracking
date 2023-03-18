# Trabalho Final - Javaee

Configurações
==============

Para poder executar você já deve ter em sua maquina o docker e docker-compose instalados.

Apos fazer o git clone do pacote dos projetos você deve fazer a configuração dos dados do serviço de email no arquivo application.properties.

Depois alterar o serviço ms-post para setar o email from na classe PostageModelToEmailPostageMapper pois ficou fixo no código.


Como usar
==============

Primeiro passo você precisa executar o comando abaixo para subir os serviços
docker-compose up -d

Depois que os serviços subirem você deve fazer o start dos projetos, ms-post, ms-email, ms-tracking

Reflexão
==============

Tecnologias que podem ser utilizada em cada camada
    - Poderia utilizar uma camada de log com interceptors
    - Ter a camada de autenticação para poder validar quem está tentando fazer requisições na api
    - Load balancer para distribuir a carga caso tiver muitos acessos 
    - Serviço de registro e descoberta para quando o autoScaling subir um maqui ele saber para onde apontar
    - Serviço de monitoramento de erros para poder notificar quando a aplicação estiver dando uma quantidade de erros exemplo: sentry 