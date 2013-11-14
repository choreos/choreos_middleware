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

ruby_block "remove-service836b83ab-0b98-4f85-b411-1f4b0cea5615" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service836b83ab-0b98-4f85-b411-1f4b0cea5615::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/836b83ab-0b98-4f85-b411-1f4b0cea5615.war]", :immediately
end

ruby_block "remove-service836b83ab-0b98-4f85-b411-1f4b0cea5615" do
  block do
  	node.run_list.remove("recipe[service836b83ab-0b98-4f85-b411-1f4b0cea5615::war]")
  end
  only_if { node.run_list.include?("recipe[service836b83ab-0b98-4f85-b411-1f4b0cea5615::war]") }
end

ruby_block "remove-deactivate-service836b83ab-0b98-4f85-b411-1f4b0cea5615" do
  block do
    node.run_list.remove("recipe[deactivate-service836b83ab-0b98-4f85-b411-1f4b0cea5615]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service836b83ab-0b98-4f85-b411-1f4b0cea5615]") }
end
