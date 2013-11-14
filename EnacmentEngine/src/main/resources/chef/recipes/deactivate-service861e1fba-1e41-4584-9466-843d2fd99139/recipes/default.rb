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

ruby_block "remove-service861e1fba-1e41-4584-9466-843d2fd99139" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service861e1fba-1e41-4584-9466-843d2fd99139::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/861e1fba-1e41-4584-9466-843d2fd99139.war]", :immediately
end

ruby_block "remove-service861e1fba-1e41-4584-9466-843d2fd99139" do
  block do
  	node.run_list.remove("recipe[service861e1fba-1e41-4584-9466-843d2fd99139::war]")
  end
  only_if { node.run_list.include?("recipe[service861e1fba-1e41-4584-9466-843d2fd99139::war]") }
end

ruby_block "remove-deactivate-service861e1fba-1e41-4584-9466-843d2fd99139" do
  block do
    node.run_list.remove("recipe[deactivate-service861e1fba-1e41-4584-9466-843d2fd99139]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service861e1fba-1e41-4584-9466-843d2fd99139]") }
end
