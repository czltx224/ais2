<% 
Runtime lRuntime = Runtime.getRuntime(); 
out.println("unit(M) "); 
out.println("FreeMemory:"+lRuntime.freeMemory()/1024/1024+" "); 
out.println("MaxMemory: "+lRuntime.maxMemory()/1024/1024+" "); 
out.println("Total Memory: "+lRuntime.totalMemory()/1024/1024+" "); 
out.println("Available Processors : "+lRuntime.availableProcessors()+" "); 
%>

<script language=javascript>

t=window.setInterval("a()",3000);

function a()
{
 window.location.reload();
}
</script>
