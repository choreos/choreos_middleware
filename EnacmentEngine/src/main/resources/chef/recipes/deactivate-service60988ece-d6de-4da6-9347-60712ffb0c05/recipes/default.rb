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

ruby_block "remove-service60988ece-d6de-4da6-9347-60712ffb0c05" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service60988ece-d6de-4da6-9347-60712ffb0c05::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/60988ece-d6de-4da6-9347-60712ffb0c05.war]", :immediately
end

ruby_block "remove-service60988ece-d6de-4da6-9347-60712ffb0c05" do
  block do
  	node.run_list.remove("recipe[service60988ece-d6de-4da6-9347-60712ffb0c05::war]")
  end
  only_if { node.run_list.include?("recipe[service60988ece-d6de-4da6-9347-60712ffb0c05::war]") }
end

ruby_block "remove-deactivate-service60988ece-d6de-4da6-9347-60712ffb0c05" do
  block do
    node.run_list.remove("recipe[deactivate-service60988ece-d6de-4da6-9347-60712ffb0c05]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service60988ece-d6de-4da6-9347-60712ffb0c05]") }
end
