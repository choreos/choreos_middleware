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

#ruby_block "disable-servicef56d9e29-83cc-40b9-bfa6-0f29545de4ab" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[servicef56d9e29-83cc-40b9-bfa6-0f29545de4ab::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['f56d9e29-83cc-40b9-bfa6-0f29545de4ab']['InstallationDir']}/service-f56d9e29-83cc-40b9-bfa6-0f29545de4ab.jar]", :immediately
#end

ruby_block "remove-servicef56d9e29-83cc-40b9-bfa6-0f29545de4ab" do
  block do
  	node.run_list.remove("recipe[servicef56d9e29-83cc-40b9-bfa6-0f29545de4ab::jar]")
  end
  only_if { node.run_list.include?("recipe[servicef56d9e29-83cc-40b9-bfa6-0f29545de4ab::jar]") }
end

ruby_block "remove-deactivate-servicef56d9e29-83cc-40b9-bfa6-0f29545de4ab" do
  block do
    node.run_list.remove("recipe[deactivate-servicef56d9e29-83cc-40b9-bfa6-0f29545de4ab]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicef56d9e29-83cc-40b9-bfa6-0f29545de4ab]") }
end
