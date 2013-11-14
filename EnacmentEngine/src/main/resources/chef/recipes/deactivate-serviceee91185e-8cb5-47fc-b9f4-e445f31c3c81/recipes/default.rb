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

ruby_block "remove-serviceee91185e-8cb5-47fc-b9f4-e445f31c3c81" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[serviceee91185e-8cb5-47fc-b9f4-e445f31c3c81::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/ee91185e-8cb5-47fc-b9f4-e445f31c3c81.war]", :immediately
end

ruby_block "remove-serviceee91185e-8cb5-47fc-b9f4-e445f31c3c81" do
  block do
  	node.run_list.remove("recipe[serviceee91185e-8cb5-47fc-b9f4-e445f31c3c81::war]")
  end
  only_if { node.run_list.include?("recipe[serviceee91185e-8cb5-47fc-b9f4-e445f31c3c81::war]") }
end

ruby_block "remove-deactivate-serviceee91185e-8cb5-47fc-b9f4-e445f31c3c81" do
  block do
    node.run_list.remove("recipe[deactivate-serviceee91185e-8cb5-47fc-b9f4-e445f31c3c81]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceee91185e-8cb5-47fc-b9f4-e445f31c3c81]") }
end
