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

ruby_block "remove-servicebe9f72b0-22e9-4554-9071-ced0c28f39ba" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicebe9f72b0-22e9-4554-9071-ced0c28f39ba::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/be9f72b0-22e9-4554-9071-ced0c28f39ba.war]", :immediately
end

ruby_block "remove-servicebe9f72b0-22e9-4554-9071-ced0c28f39ba" do
  block do
  	node.run_list.remove("recipe[servicebe9f72b0-22e9-4554-9071-ced0c28f39ba::war]")
  end
  only_if { node.run_list.include?("recipe[servicebe9f72b0-22e9-4554-9071-ced0c28f39ba::war]") }
end

ruby_block "remove-deactivate-servicebe9f72b0-22e9-4554-9071-ced0c28f39ba" do
  block do
    node.run_list.remove("recipe[deactivate-servicebe9f72b0-22e9-4554-9071-ced0c28f39ba]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicebe9f72b0-22e9-4554-9071-ced0c28f39ba]") }
end
