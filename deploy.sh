sed -ie 's|file:/home/bob/what/staging/|http://jadn.com/what/|g' dist/webstart/application.jnlp 
rsync -r dist/webstart/* jadn.com:/jadn/what
