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

ruby_block "remove-service9e826bb6-0ce2-40af-b8bb-7a8ab05fa3be" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service9e826bb6-0ce2-40af-b8bb-7a8ab05fa3be::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/9e826bb6-0ce2-40af-b8bb-7a8ab05fa3be.war]", :immediately
end

ruby_block "remove-service9e826bb6-0ce2-40af-b8bb-7a8ab05fa3be" do
  block do
  	node.run_list.remove("recipe[service9e826bb6-0ce2-40af-b8bb-7a8ab05fa3be::war]")
  end
  only_if { node.run_list.include?("recipe[service9e826bb6-0ce2-40af-b8bb-7a8ab05fa3be::war]") }
end

ruby_block "remove-deactivate-service9e826bb6-0ce2-40af-b8bb-7a8ab05fa3be" do
  block do
    node.run_list.remove("recipe[deactivate-service9e826bb6-0ce2-40af-b8bb-7a8ab05fa3be]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service9e826bb6-0ce2-40af-b8bb-7a8ab05fa3be]") }
end
