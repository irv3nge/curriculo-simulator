<h1>📄 Sistema de Candidatura com Spring Boot, H2, RabbitMQ e Gmail</h1>

<p>
Este projeto é um sistema para <strong>recebimento de currículos</strong> com formulário HTML/CSS puro e backend em <strong>Java Spring Boot</strong>.
Ao preencher o formulário e enviar um PDF, o sistema:
</p>

<ol>
  <li>Salva o arquivo PDF localmente.</li>
  <li>Registra os dados da candidatura no banco H2.</li>
  <li>Publica uma mensagem no RabbitMQ com as informações recebidas.</li>
  <li>Um consumidor lê a fila e envia um e-mail via Gmail com o PDF anexado.</li>
</ol>

<h2>🚀 Tecnologias Utilizadas</h2>
<ul>
  <li>Java 17+</li>
  <li>Spring Boot 3.3 (Web, Validation, Data JPA, AMQP, Mail)</li>
  <li>RabbitMQ</li>
  <li>Banco de dados H2</li>
  <li>HTML5, CSS3 e JavaScript puro</li>
  <li>SMTP Gmail (com senha de aplicativo)</li>
</ul>

<h2>⚙️ Como Utilizar</h2>
<ol>
  <li>Instale e inicie o <strong>RabbitMQ</strong> localmente.</li>
  <li>Configure seu <strong>Gmail</strong> com senha de aplicativo e ajuste no arquivo <code>application.yml</code> do projeto.</li>
  <li>Compile e execute o projeto com <code>mvn spring-boot:run</code>.</li>
  <li>Acesse o formulário pelo navegador em <a href="http://localhost:8080">http://localhost:8080</a>.</li>
  <li>Preencha os dados, anexe seu currículo em PDF e envie.</li>
</ol>

<h2>📬 Funcionalidades Principais</h2>
<ul>
  <li>Formulário web responsivo em HTML e CSS.</li>
  <li>Envio de arquivo PDF com validação de tamanho e tipo.</li>
  <li>Armazenamento dos dados no banco H2.</li>
  <li>Integração com RabbitMQ para mensageria.</li>
  <li>Envio automático de e-mail com anexo via Gmail SMTP.</li>
</ul>

<h2>🗄️ Banco de Dados</h2>
<p>
O projeto utiliza o banco <strong>H2</strong> no modo file-based, permitindo acesso ao console web em:
</p>
<ul>
  <li>URL: <a href="http://localhost:8080/h2-console">http://localhost:8080/h2-console</a></li>
  <li>JDBC URL: <code>jdbc:h2:file:./data/curriculos-db</code></li>
  <li>Usuário: <code>sa</code></li>
  <li>Senha: (vazio)</li>
</ul>


<h2>📝 Licença</h2>
<p>
Este projeto é livre para estudos, testes e personalizações.
</p>
