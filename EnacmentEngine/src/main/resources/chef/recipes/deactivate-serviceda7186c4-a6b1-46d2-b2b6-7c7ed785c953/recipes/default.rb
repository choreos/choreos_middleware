#
# Cookbook Name:: generic-jar-service-template
# Recipe:: default
#
# Copyright 2012, YOUR_COMPANY_NAME
#
# All rights reserved - Do Not Redistribute
#

##########################################################################
#                  #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                  #
# All ocurrences of $ NAME must be replaced with the actual service name #
#            before uploading the recipe to the chef-server              #
#                  #
##########################################################################

ruby_block "disable-serviceda7186c4-a6b1-46d2-b2b6-7c7ed785c953" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[serviceda7186c4-a6b1-46d2-b2b6-7c7ed785c953::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['da7186c4-a6b1-46d2-b2b6-7c7ed785c953']['InstallationDir']}/service-da7186c4-a6b1-46d2-b2b6-7c7ed785c953.jar]", :immediately
end

ruby_block "remove-serviceda7186c4-a6b1-46d2-b2b6-7c7ed785c953" do
  block do
  	node.run_list.remove("recipe[serviceda7186c4-a6b1-46d2-b2b6-7c7ed785c953::jar]")
  end
  only_if { node.run_list.include?("recipe[serviceda7186c4-a6b1-46d2-b2b6-7c7ed785c953::jar]") }
end

ruby_block "remove-deactivate-serviceda7186c4-a6b1-46d2-b2b6-7c7ed785c953" do
  block do
    node.run_list.remove("recipe[deactivate-serviceda7186c4-a6b1-46d2-b2b6-7c7ed785c953]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceda7186c4-a6b1-46d2-b2b6-7c7ed785c953]") }
end
