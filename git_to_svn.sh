# ainda não é um script
# Autores: Leonardo, Cadu

git_to_svn -> git pull
cloud -> svn up

git log --oneline | cut -d ' ' -f2- | xclip (cola com "três dedos") // tira linha antigas

git chekcout (cópia limpa do repositório) // git_to_svn

rm -rf ow2forge/.../cloud
cp -R ~/workspaces/choreos/git_to_svn/ cloud

svn add --force cloud/
cd cloud
svn st | grep '^!' | cut -d '!' -f2 | xargs svn rm
