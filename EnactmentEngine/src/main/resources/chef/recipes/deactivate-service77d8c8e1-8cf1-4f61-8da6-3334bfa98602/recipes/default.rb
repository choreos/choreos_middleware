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

#ruby_block "disable-service77d8c8e1-8cf1-4f61-8da6-3334bfa98602" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service77d8c8e1-8cf1-4f61-8da6-3334bfa98602::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['77d8c8e1-8cf1-4f61-8da6-3334bfa98602']['InstallationDir']}/service-77d8c8e1-8cf1-4f61-8da6-3334bfa98602.jar]", :immediately
#end

ruby_block "remove-service77d8c8e1-8cf1-4f61-8da6-3334bfa98602" do
  block do
  	node.run_list.remove("recipe[service77d8c8e1-8cf1-4f61-8da6-3334bfa98602::jar]")
  end
  only_if { node.run_list.include?("recipe[service77d8c8e1-8cf1-4f61-8da6-3334bfa98602::jar]") }
end

ruby_block "remove-deactivate-service77d8c8e1-8cf1-4f61-8da6-3334bfa98602" do
  block do
    node.run_list.remove("recipe[deactivate-service77d8c8e1-8cf1-4f61-8da6-3334bfa98602]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service77d8c8e1-8cf1-4f61-8da6-3334bfa98602]") }
end
