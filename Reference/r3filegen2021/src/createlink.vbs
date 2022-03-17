set WshShell = WScript.CreateObject("WScript.Shell")
strDesktop = WshShell.SpecialFolders("AllUsersDesktop")
curp = WScript.CreateObject("Scripting.FileSystemObject").GetAbsolutePathName(".")
set oShellLink = WshShell.CreateShortcut(strDesktop & "\R3 Filge Generator.lnk")

set filesys = CreateObject("Scripting.FileSystemObject")
Set f = filesys.GetSpecialFolder(1)
execfile = f & "\javaw.exe -jar " & curp & "\r3filegen.jar" 
rem WsCript.Echo "path is " &  execfile 
oShellLink.TargetPath = curp & "\r3filegen.jar"
oShellLink.WindowStyle = 1
oShellLink.Description = "R3 File Generator"
oShellLink.WorkingDirectory = curp & "\"
oShellLink.IconLocation = f & "\java.exe"
oShellLink.Save

com = "cmd /K c: & cd " & strDesktop & " & jar xvf " & curp & "\lib\image.jar"
WsCript.echo "desktop " & strDesktop & " command " & com
WshShell.run com
Set OshellLink = Nothing
set WshShell = Nothing
rem For Each strFolder In WshShell.SpecialFolders
rem       MsgBox strFolder
rem Next
