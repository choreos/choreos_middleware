
# NOTE: this is a template file. $variables must be properly replaced.

include_recipe "apt" # java recipe is failing without recipe apt (and tomcat depends on java)
include_recipe "tomcat::choreos"

if not node['CHOReOSData']['serviceData']['$NAME']['deactivate']
	remote_file "war_file" do
  		source "#{node['CHOReOSData']['serviceData']['$NAME']['PackageURL']}"
  		path "#{node['tomcat']['webapp_dir']}/$NAME.war"
  		mode "0755"
  		action :create_if_missing
	end
end

file "#{node['tomcat']['webapp_dir']}/$NAME.war" do
	action :nothing
end

if node['CHOReOSData']['serviceData']['$NAME']['deactivate']
	ruby_block "remove-service-$NAME" do
		block do
			i=0
		end
		notifies :delete, "file[#{node['tomcat']['webapp_dir']}/$NAME.war]"
	end
end
