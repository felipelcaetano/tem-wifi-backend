# Tem Wi-Fi? Vibbra

Plataforma pare encontro de lugares públicos para se trabalhar, avaliar os lugares e
consultar avaliações de outras pessoas.

## Iniciando

O projeto está dividido em duas partes. Front-End, feito em Angular 7, e Back-End feito em Java 8.
Eles podem ser baixados dos respositórios no GitHub em
<https://github.com/felipelcaetano/tem-wifi-frontend> e
<https://github.com/felipelcaetano/tem-wifi-backend>.

O Front-end pode ser executado localmente, porém o Back-end está em uma estrutura Serverless
na nuvem da AWS, impedindo sua execução local sem que o usuário tenha acesso ao rescursos da conta
AWS.

Algumas funcionalidades não funcionam localmente por questões de segurança, como o login através
 das redes Sociais Facebook e Google e a pesquisa de endereço pela Google Maps API.

### Pré Requisitos

Você pode gerar uma chave de API do Google em sua conta do Google Cloud, liberando os serviços
Api Maps e Places, sem restrição de origem de endereço para que você consiga testar localmente
essas funções.

Para isso substitua a chave de api nas variáveis de ambiente, nos arquivos environment.ts e environment.prod.ts

```
googleMapsPlaceApiKey: '<SUA_KEY>'
```

### Instalação

Para começar a utilizar o sistema no ambiente de desenvolvimento, sem a necessidade de nenhuma
instalção, basta acessar a url <https://dlp56gvummmje.cloudfront.net>, se cadastrar e pronto.

Caso queria testar o Front-End localmente, siga os passos:

#### Instalar Node.js version 8.x or 10.x:
- Para checar sua versão, execute node -v em um terminal/console window.
- Para fazer o donwload do Node.js, vá para [nodejs.org](https://nodejs.org/en/).

#### Instalar o gerenciador de pacotes do Node.js [NPM](https://www.npmjs.com/get-npm)

#### Instalar o [Angular CLI](https://angular.io/guide/quickstart) global:
```
npm install -g @angular/cli
```

#### Dentro da pasta raíz do projeto, instalar as dependências do projeto:
```
npm i
```

#### Por último, execultar o comando para iniciar a aplicação localmente em uma nova aba do seu navegador:
```
ng serve --open
```

#### Observação Back-End
Por em quanto não é possível testar o Back-End localmente, desacoplado do Front, pois é
necessário uma conta na AWS em qualquer integração que venha a ser feita pelo sistema,
como por exemplo com banco de dados.

## Implantação

Ainda não é possível fazer a implantação de novas versões sem que seja feito por alguém com
acesso a conta [AWS](https://aws.amazon.com/pt/) onde os recursos estão hospedados.

## Construído com

* [Angular 7](https://angular.io/) - Framework web usado
* [NPM](https://www.npmjs.com) - Gerenciador de pacotes do Node, para o Front-End
* [Java 8](https://www.java.com/pt_BR/) - Linguagem do Back-End
* [Dagger 2](https://google.github.io/dagger/) - Injetor de dependências utilizado no Back-End
* [Maven](https://maven.apache.org/) - Gerenciamento de Dependência para o Back-End
* [AWS](https://aws.amazon.com/pt/) - Infraestrutura
* [AWS S3](https://aws.amazon.com/pt/s3) - Armazenamento de arquivos e host para o site
* [AWS CloudFront](https://aws.amazon.com/pt/cloudfront) - Distribuição do site estático do S3
e emissor de certificado SSL para conexões seguras
* [AWS Api Gateway](https://aws.amazon.com/pt/api-gateway) - Gerenciador das APIs do Back-End
e integrador das chamadas junto a função lambda responsável por processar a requisição
* [AWS Lambda](https://aws.amazon.com/pt/lambda) - Serviço computacional, responsável por todo
processamento de requisições as APIs do sistema, onde estão os códigos em Java
* [AWS DynamoDB](https://aws.amazon.com/pt/dynamodb) - Banco de dados NoSQL, auto gerenciado
pela AWS, responsável por todo armazenamento de dados da aplicação


## Autores

* [Felipe Caetano](https://github.com/felipelcaetano) - *Trabalho inicial*


## Licença

Esse projeto está sob a licença MIT License

## Problemas conhecidos

* Login via Facebook loga automaticamente 1x ao deslogar
* Login via Google não está funcionando

## Lista de Endpoints disponíveis para teste

Url base: https://33arogiloa.execute-api.us-east-1.amazonaws.com/dev

Passar no Header da requisição a chave para testes (possui limites de execução por dia e/ou mês):
```
X-Api-Key : g3s0CsNIiS7ggJ9D1ytQYXV6AXTlsXQ3lVPMQxS6
```

```
/auth/user - POST - cadastro de usuário
request body:
{
    "id": "email",
    "pass": "senha"
}

response:
{
    "id": "id do usuário",
    "email": "email do usuário",
    "token": "OAuth token"
}
```
```
/auth/login - POST - login e geração de token
request body:
{
    "id": "email",
    "pass": "senha"
}

response:
{
    "id": "id do usuário",
    "email": "email do usuário",
    "token": "OAuth token"
}
```
```
/location - POST - Cadastro de nova localização*
request body:
{
    "type": "tipo do local",
    "name": "nome do local",
    "street": "logradouro",
    "number": "número",
    "complement": "complemento",
    "city": "cidade",
    "state": "estado",
    "country": "país"
}

response:
{
    "links": [
        "href": "url do local criado + id",
        "rel": "id do local criado"
    ]
}
```
```
/location/{locationId} - GET - Consulta de localização*

response:
{
    "type": "tipo do local",
    "name": "nome do local",
    "street": "logradouro",
    "number": "número",
    "complement": "complemento",
    "city": "cidade",
    "state": "estado",
    "country": "país"
    "ratingCounts": 0 (total de avaliações),
    "ratings" [
        "lista dos ids das avaliações do local"
    ]
}
```
```
/rating - POST - Cadastro de nova avaliação*

request:
{
    "locationId": "id do local da avaliação",
    "userId": "id do usuário logado",
    "foods": "comidas servidas no local",
    "drinks": "bebidas servidas no local",
    "treatment": 0 (avaliação do atendimento de 1 a 5),
    "price": 0 (avaliação do preço de 1 a 5),
    "comfort": 0 (avaliação do conforto de 1 a 5),
    "noise": 0 (avaliação do ruído de 1 a 5),
    "generalRating": 0 (avaliação do atendimento geral de 1 a 5),
    "internet" : {
        "hasInternet": true/false (se local possúi internet),
        "speed": 0 (velocidade da internet em MB),
        "isOpened" true/false (se a internet é aberta),
        "pass": "senha da internet"
    }
}

response:
{
    "links": [
        "href": "url da avaliação criada + id",
        "rel": "id da avaliação criada"
    ]
}
```
```
/rating - GET - Consulta de avaliações de um usuário*

response:
{
    "ratings": [
        {
            "id": "id da avaliação",
            "locationId": "id do local da avaliação",
            "userId": "id do usuário que efetuou a avaliação",
            "foods": "comidas servidas no local",
            "drinks": "bebidas servidas no local",
            "treatment": 0 (avaliação do atendimento de 1 a 5),
            "price": 0 (avaliação do preço de 1 a 5),
            "comfort": 0 (avaliação do conforto de 1 a 5),
            "noise": 0 (avaliação do ruído de 1 a 5),
            "generalRating": 0 (avaliação do atendimento geral de 1 a 5),
            "internet" : {
                "hasInternet": true/false (se local possúi internet),
                "speed": 0 (velocidade da internet em MB),
                "isOpened" true/false (se a internet é aberta),
                "pass": "senha da internet"
            }
        }
    ],
    "links": [
        "href": "url da avaliação + id",
        "rel": "id da avaliação"
    ]
}
```
```
/rating/{ratingId} - GET - Consulta de avaliação*

response:
{
    "id": "id da avaliação",
    "locationId": "id do local da avaliação",
    "userId": "id do usuário que efetuou a avaliação",
    "foods": "comidas servidas no local",
    "drinks": "bebidas servidas no local",
    "treatment": 0 (avaliação do atendimento de 1 a 5),
    "price": 0 (avaliação do preço de 1 a 5),
    "comfort": 0 (avaliação do conforto de 1 a 5),
    "noise": 0 (avaliação do ruído de 1 a 5),
    "generalRating": 0 (avaliação do atendimento geral de 1 a 5),
    "internet" : {
        "hasInternet": true/false (se local possúi internet),
        "speed": 0 (velocidade da internet em MB),
        "isOpened" true/false (se a internet é aberta),
        "pass": "senha da internet"
    }
}
```
```
/rating/{ratingId} - PUT - alteração de avaliação*

request:
{
    "id": "id da avaliação",
    "foods": "comidas servidas no local",
    "drinks": "bebidas servidas no local",
    "treatment": 0 (avaliação do atendimento de 1 a 5),
    "price": 0 (avaliação do preço de 1 a 5),
    "comfort": 0 (avaliação do conforto de 1 a 5),
    "noise": 0 (avaliação do ruído de 1 a 5),
    "generalRating": 0 (avaliação do atendimento geral de 1 a 5),
    "internet" : {
        "hasInternet": true/false (se local possúi internet),
        "speed": 0 (velocidade da internet em MB),
        "isOpened" true/false (se a internet é aberta),
        "pass": "senha da internet"
    }
}

response:
no data
```
*Necessita de token gerado previamento e informado no header Authorization