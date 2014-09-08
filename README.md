CacheRegionsClearingTool
========================

This OSGi Plugin clears specified cache regions on a scheduled basis: By default, it runs 60 seconds after the plugin is added to a dotcms environment or after startup. This value can be edited in Activator class:

```
Calendar cal = Calendar.getInstance();
cal.add(Calendar.SECOND, 60);

String cron = new SimpleDateFormat("ss mm H d M ? yyyy").format(cal.getTime());

```
Or you can set up your own cron expression:

```
String cron = "0 0 0 1/1 * ? *";//Every day at 00:00 server time
```


Cache regions can be also specified in the Activator class:

```
Map<String, Object> params = new HashMap<String, Object>();
params.put( "param1", "Host" );
params.put( "param2", "Page" );
```

Keep in mind that key values must start with "param" and all numbers should be consecutive, starting from 1.

Cache Regions:

```
Permission
CMS Role
Role
Category
Contentlet
Chain
LogMapper
Relationship
Plugin
Language
User
Velocity
Layout
User Proxy
Host
File
Page
Menu Link
Container
Template
Identifier
Versionable
FolderCache
Workflow Cache
Virtual Link Cache
Host Variables
Block Directive
Indicies
Navigation Tool
PublishingEndPoint Cache
PushedAssets Cache
```

