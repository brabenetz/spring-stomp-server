rmdir latest
rm latest
mklink /D latest 1.0.0
rmdir current
rm current
mklink /D current 1.0.0
cd ..
rmdir currentrelease
rm currentrelease
mklink /D currentrelease archiv\1.0.0
cd archiv
pause
