# Authors: Leonardo, Cadu
if [[ $# != 2 || ! -d $1 || ! -d $2 ]]; then
    echo "Usage: $(basename $0) [git folder] [svn folder]"
    exit
fi

# _Clean_ git tree source
GIT=$(realpath $1)
#  Subversion folder to be completely substituted by the git one
SVN=$(realpath $2)

if [ -d "$SVN/.svn" ]; then
    echo "There's a .svn folder in ${SVN}. Quitting."
    exit
fi

cd $GIT; git pull
cd $SVN; svn up

rm -rf $SVN
cd $(dirname $SVN)
cp -R $GIT $(basename $SVN)

cd $(basename $SVN)
rm -rf .git/
svn add --force .
svn st | grep '^!' | cut -d '!' -f 2 | xargs svn rm

cd $GIT
msg=$(mktemp)
git log --oneline | cut -d ' ' -f 2- >$msg
echo 'Press any key to edit svn commit message...'
read
$EDITOR $msg

cd $SVN
svn ci -F $msg

rm -f $msg
echo 'Finished'
