# SkyStaff
A minecraft plugin that hooks into BungeeCord &amp; LuckPerms to display all servers, and their staff.

This plugin only hooks into LuckPerms and no other permissions plugin, you must have this plugin installed before attempting to use this plugin.

![alt Image](https://cdn.discordapp.com/attachments/892102571974619148/946077619831574589/Screenshot_1.png)

## Setup
Simply place the jar into your plugins folder, and then start & stop your server.

From there, a config file will generate, and you can configure it how you want.

Once you have finished configuring the file, you can then start the server to see the changes.

## config.yml

```yml
utils:
  noPerm: "&cI'm sorry, but you do not have permission to perform this command. Please contact the server administrators if you believe that this is in error."
  staffPermission: 'server.staff'
  adminPermission: 'server.admin'
  showServersWithoutStaff: true
  
staffCommand:
  header: ' &9&lOnline Staff &7({players}/{maxPlayers})'
  serverLine: '&6- &a{server} &7({players})'
  staffLine: '  &f- {prefix}{player} &7[{time}&7]'
  noStaffLine: ' &cNo staff is online.'
  noStaffOnlineLine: ' &cThere are currently no members of staff to display.'

alertsCommand:
  toggle: '&dYou have &f{mode} &dyour staff alerts.'

staffChat:
  format: '&9[S] {rank}{player} &7{server} &f» &f{content}'
  invalid: '&cInvalid Usages! Usage: /staffchat <content: String>'


hideCommand:
  hideLine: '&dYou have &f{mode} &dyour staff list visibility.'

staffAlerts:
  connected: '&9[S] &f{player} &dhas connected to &e{server}&d.'
  switched: '&9[S] &f{player} &dhas moved from &e{oldServer} &dto &e{newServer}&d!'
  disconnected: '&9[S] &5&l<!> &f{player} &dhas disconnected from &e{server}&d!'


servers:
  - hub-01
  - hub-02
  - prison
  - skyblock
  - minigames
```

If you have any problems with this plugin, contact me on Discord @ marcuz#9158.
