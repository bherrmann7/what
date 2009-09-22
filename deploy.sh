
sed -i -e's|codebase="file:/home/bob/what/target/"|codebase="http://jadn.com/what/"|' target/application.jnlp
rsync -r target/* jadn.com:/jadn/what


