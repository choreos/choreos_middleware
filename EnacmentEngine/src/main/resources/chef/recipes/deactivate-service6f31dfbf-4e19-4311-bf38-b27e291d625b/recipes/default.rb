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

ruby_block "remove-service6f31dfbf-4e19-4311-bf38-b27e291d625b" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service6f31dfbf-4e19-4311-bf38-b27e291d625b::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/6f31dfbf-4e19-4311-bf38-b27e291d625b.war]", :immediately
end

ruby_block "remove-service6f31dfbf-4e19-4311-bf38-b27e291d625b" do
  block do
  	node.run_list.remove("recipe[service6f31dfbf-4e19-4311-bf38-b27e291d625b::war]")
  end
  only_if { node.run_list.include?("recipe[service6f31dfbf-4e19-4311-bf38-b27e291d625b::war]") }
end

ruby_block "remove-deactivate-service6f31dfbf-4e19-4311-bf38-b27e291d625b" do
  block do
    node.run_list.remove("recipe[deactivate-service6f31dfbf-4e19-4311-bf38-b27e291d625b]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service6f31dfbf-4e19-4311-bf38-b27e291d625b]") }
end
