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

ruby_block "remove-serviced96e50b6-e8af-4bc5-b262-04461b347f04" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[serviced96e50b6-e8af-4bc5-b262-04461b347f04::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/d96e50b6-e8af-4bc5-b262-04461b347f04.war]", :immediately
end

ruby_block "remove-serviced96e50b6-e8af-4bc5-b262-04461b347f04" do
  block do
  	node.run_list.remove("recipe[serviced96e50b6-e8af-4bc5-b262-04461b347f04::war]")
  end
  only_if { node.run_list.include?("recipe[serviced96e50b6-e8af-4bc5-b262-04461b347f04::war]") }
end

ruby_block "remove-deactivate-serviced96e50b6-e8af-4bc5-b262-04461b347f04" do
  block do
    node.run_list.remove("recipe[deactivate-serviced96e50b6-e8af-4bc5-b262-04461b347f04]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviced96e50b6-e8af-4bc5-b262-04461b347f04]") }
end
