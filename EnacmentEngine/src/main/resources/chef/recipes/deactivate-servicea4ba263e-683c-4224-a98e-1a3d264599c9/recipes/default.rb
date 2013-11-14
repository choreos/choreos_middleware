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

ruby_block "remove-servicea4ba263e-683c-4224-a98e-1a3d264599c9" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicea4ba263e-683c-4224-a98e-1a3d264599c9::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/a4ba263e-683c-4224-a98e-1a3d264599c9.war]", :immediately
end

ruby_block "remove-servicea4ba263e-683c-4224-a98e-1a3d264599c9" do
  block do
  	node.run_list.remove("recipe[servicea4ba263e-683c-4224-a98e-1a3d264599c9::war]")
  end
  only_if { node.run_list.include?("recipe[servicea4ba263e-683c-4224-a98e-1a3d264599c9::war]") }
end

ruby_block "remove-deactivate-servicea4ba263e-683c-4224-a98e-1a3d264599c9" do
  block do
    node.run_list.remove("recipe[deactivate-servicea4ba263e-683c-4224-a98e-1a3d264599c9]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicea4ba263e-683c-4224-a98e-1a3d264599c9]") }
end
