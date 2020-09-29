# Tecnologias:
	
<ol>
	<li>Java 1.8</li>
	<li>Maven 3.X</li>
	<li>Singular 1.8.0-RC3</li>
	<li>H2 Database</li>
</ol>

# Deploy da aplicação:
	Compile o projeto com o comando "mvn clean install".
	Para realizar o deploy da aplicação é necessário utilizar o Wildfly 10.0.1 
	disponibilizado pela equipe Singular, 
	disponível para download [aqui](https://github.com/opensingular/singular-platform/releases/tag/1.1.0)
	Descompacte o arquivo baixado no link anterior, 
	copie todos os WAR's gerados na compilação para a pasta /wildfly/standalone/deployments. 
	Entre na pasta /bin gerada na descompactação e execute o comando ./singular.sh para linux 
	ou ./singular.bat para windows.
	
	Após subir o projeto, acesse o https://localhost:8443/ e 
	comece a usar a aplicação normalmente, por padrão o sistema aceita qualquer texto como login e senha.

# Este projeto contém
	-> Formulário "MyRequirement":
				<ul>
					<li>Campo radio button com validação</li>
					<li>Campo com exibição dinâmica</li>
					<li>Campo textarea, para textos grandes</li>
					<li>DropDown comum</li>
					<li>Autocomplete</li>
				<ul>
	 -> Formulário "CadastroPessoa":
	 			<ul>
					<li>1 Custom SType para dados pessoais</li>
					<li>Campos com validação interdependentes (Nome e Sobrenome, valida se não foi inserido errôneamente)</li>
					<li>Validação de CPF</li>
					<li>Campo para telefone nacional</li>
					<li>Campo com updateListener para preenchimento automático da idade</li>
					<li>1 STypeAddress</li>
					<li>Campo com updateListener para busca de dados com ViaCEP</li>
				<ul>
		-> Custom Service com integração à serviços de busca de endereço por CEP
		-> Caixa dinâmica para listagem de cadastros com necessidade de aprovação do ADMIN
		-> Etapas de análises gerenciais, com possibilidade de anotação.
