---
layout: post
title:  Como atualizar um repositório fork no GitHub
date:   2017-07-10
category: Git
author: Gustavo Furtado de Oliveira Alves
image: /images/git.jpg
---

Neste post iremos mostrar como atualizar o repositório fork do git, com os commits do repositório de origem.

##O Problema

Quando vamos contribuir em um projeto **Open Source** no GitHub,
temos que fazer um fork do repositório do projeto para a nossa conta.

Após realizar a nossa contribuição no fork, fazemos o **Pull Request** para integrar os nossos commits
ao repositório de origem do projeto.

Entretanto é natural que nosso repositório _fork_ fique defasado com o tempo.
Ou seja, houve novos commits no repositório de origem desde o momento em que o _fork_ foi criado.

Neste momento precisamos atualizar o nosso repositório _fork_ com os commits realizados no repositório de origem.

Vamos ver como fazer isso.

##Atualizando o Fork

Vamos pegar como exemplo o [repositório](https://github.com/pedrohrr/fullstackninja.com.br) deste próprio blog.
No momento em que escrevo este post, [meu repositório fork](https://github.com/gustavofoa/fullstackninja.com.br)
está desatualizado em relação ao repositório de origem.

Abaixo você pode ver que o fork que tá na minha conta do github tem 5 commits.

![Fork desatualizado](/images/20170710/fork-desatualizado.png){:width=100%}

E o repositório de origem que está na conta do Pedro tem 16 commits.

![Repositório de origem atualizado](/images/20170710/repo-origem-atualizado.png){:width=100%}

Ou seja, meu repositório fork está 11 commits desatualizados.

Entendendo que o git é um sistema de controle de versão distribuído,
podemos seguir os seguintes passos para atualizar o nosso repositório fork.

1. Baixar o cógido do fork no meu computador com o comando abaixo.

```
git clone https://github.com/gustavofoa/fullstackninja.com.br.git
cd fullstackninja.com.br
```

![Git clone](/images/20170710/git-clone.png){:width=100%}

2. Configurar o repositório original como um repositório remoto do meu repositório local.

```
git remote add upstream https://github.com/pedrohrr/fullstackninja.com.br.git
```

![Git remote add upstream](/images/20170710/git-remote-add-upstream.png){:width=100%}

3. Baixar os commits do repositório original para a o meu repositório local.

```
git fetch upstream
```

![Git fetch upstream](/images/20170710/git-fetch-upstream.png){:width=100%}

4. Integrar os commits (merge) da branch upstream na minha branch de trabalho (master)

```
git pull upstream master
```

![Git pull upstream master](/images/20170710/git-pull-upstream-master.png){:width=100%}

5. Posso conferir que a branch master agora tem os commits que vieram do repositório de origem.

```
git status
```

![Git status](/images/20170710/commits-pendentes-de-push.png){:width=100%}

6. Realizar o push para o meu repositório fork (_origin master_).

```
git push origin master
```

![Git push](/images/20170710/git-push.png){:width=100%}

Pronto, com isso temos o nosso repositório fork atualizado.

![Fullstackninja fork](/images/20170710/fork-atualizado.png){:width=100%}

##Resumindo

Muita gente gosta de ir direto para os comandos utilizados,
por isso resolvi colocar todos os comandos aqui de forma resumida.

```
git clone https://github.com/gustavofoa/fullstackninja.com.br.git
cd fullstackninja.com.br
git remote add upstream https://github.com/pedrohrr/fullstackninja.com.br.git
git fetch upstream
git pull upstream master
git status
git push origin master
```