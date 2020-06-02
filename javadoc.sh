  echo -e "Generating javadoc...\n"
  mvn javadoc:javadoc

  echo -e "Publishing javadoc...\n"

  cp -R target/site/apidocs $HOME/javadoc-latest

  cd $HOME
  git config --global user.email "travis@travis-ci.org"
  git config --global user.name "travis-ci"
  cd gh-pages
  git branch gh-pages
  git checkout gh-pages

  echo -e "Clearing ./src ./javadoc and files...\n"
  git rm -rf ./javadoc
  git rm -rf ./src
  rm -rf ./src
  rm .gitignore
  rm .travis.yml
  rm biblioteca.iml
  rm pom.xml
  rm settings.xml
  cp -Rf $HOME/javadoc-latest ./javadoc
  git add -f .
  git commit -m "Lastest javadoc on successful travis build $TRAVIS_BUILD_NUMBER auto-pushed to gh-pages"
  git push -fq origin gh-pages #> /dev/null

  echo -e "Published Javadoc to gh-pages.\n"