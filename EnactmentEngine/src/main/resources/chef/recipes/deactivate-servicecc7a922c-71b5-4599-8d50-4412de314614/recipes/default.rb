#
# Cookbook Name:: generic-jar-service-template
# Recipe:: default
#
# Copyright 2012, YOUR_COMPANY_NAME
#
# All rights reserved - Do Not Redistribute
#

##########################################################################
#                  														 #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                  														 #
# All ocurrences of $ NAME must be replaced with the actual service name #
#            before uploading the recipe to the chef-server              #
#                  														 #
##########################################################################

ruby_block "remove-servicecc7a922c-71b5-4599-8d50-4412de314614" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicecc7a922c-71b5-4599-8d50-4412de314614::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/cc7a922c-71b5-4599-8d50-4412de314614.war]", :immediately
end

ruby_block "remove-servicecc7a922c-71b5-4599-8d50-4412de314614" do
  block do
  	node.run_list.remove("recipe[servicecc7a922c-71b5-4599-8d50-4412de314614::war]")
  end
  only_if { node.run_list.include?("recipe[servicecc7a922c-71b5-4599-8d50-4412de314614::war]") }
end

ruby_block "remove-deactivate-servicecc7a922c-71b5-4599-8d50-4412de314614" do
  block do
    node.run_list.remove("recipe[deactivate-servicecc7a922c-71b5-4599-8d50-4412de314614]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicecc7a922c-71b5-4599-8d50-4412de314614]") }
end
