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

#ruby_block "disable-service3ef127f4-349a-475f-8eb3-31d0ac7a92b8" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service3ef127f4-349a-475f-8eb3-31d0ac7a92b8::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['3ef127f4-349a-475f-8eb3-31d0ac7a92b8']['InstallationDir']}/service-3ef127f4-349a-475f-8eb3-31d0ac7a92b8.jar]", :immediately
#end

ruby_block "remove-service3ef127f4-349a-475f-8eb3-31d0ac7a92b8" do
  block do
  	node.run_list.remove("recipe[service3ef127f4-349a-475f-8eb3-31d0ac7a92b8::jar]")
  end
  only_if { node.run_list.include?("recipe[service3ef127f4-349a-475f-8eb3-31d0ac7a92b8::jar]") }
end

ruby_block "remove-deactivate-service3ef127f4-349a-475f-8eb3-31d0ac7a92b8" do
  block do
    node.run_list.remove("recipe[deactivate-service3ef127f4-349a-475f-8eb3-31d0ac7a92b8]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service3ef127f4-349a-475f-8eb3-31d0ac7a92b8]") }
end
