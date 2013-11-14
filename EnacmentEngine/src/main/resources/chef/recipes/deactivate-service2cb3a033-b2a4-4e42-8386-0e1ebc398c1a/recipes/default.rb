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

ruby_block "remove-service2cb3a033-b2a4-4e42-8386-0e1ebc398c1a" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service2cb3a033-b2a4-4e42-8386-0e1ebc398c1a::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/2cb3a033-b2a4-4e42-8386-0e1ebc398c1a.war]", :immediately
end

ruby_block "remove-service2cb3a033-b2a4-4e42-8386-0e1ebc398c1a" do
  block do
  	node.run_list.remove("recipe[service2cb3a033-b2a4-4e42-8386-0e1ebc398c1a::war]")
  end
  only_if { node.run_list.include?("recipe[service2cb3a033-b2a4-4e42-8386-0e1ebc398c1a::war]") }
end

ruby_block "remove-deactivate-service2cb3a033-b2a4-4e42-8386-0e1ebc398c1a" do
  block do
    node.run_list.remove("recipe[deactivate-service2cb3a033-b2a4-4e42-8386-0e1ebc398c1a]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service2cb3a033-b2a4-4e42-8386-0e1ebc398c1a]") }
end
