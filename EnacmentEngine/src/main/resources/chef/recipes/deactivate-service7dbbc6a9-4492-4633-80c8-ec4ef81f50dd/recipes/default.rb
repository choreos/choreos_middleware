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

ruby_block "disable-service7dbbc6a9-4492-4633-80c8-ec4ef81f50dd" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service7dbbc6a9-4492-4633-80c8-ec4ef81f50dd::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['7dbbc6a9-4492-4633-80c8-ec4ef81f50dd']['InstallationDir']}/service-7dbbc6a9-4492-4633-80c8-ec4ef81f50dd.jar]", :immediately
end

ruby_block "remove-service7dbbc6a9-4492-4633-80c8-ec4ef81f50dd" do
  block do
  	node.run_list.remove("recipe[service7dbbc6a9-4492-4633-80c8-ec4ef81f50dd::jar]")
  end
  only_if { node.run_list.include?("recipe[service7dbbc6a9-4492-4633-80c8-ec4ef81f50dd::jar]") }
end

ruby_block "remove-deactivate-service7dbbc6a9-4492-4633-80c8-ec4ef81f50dd" do
  block do
    node.run_list.remove("recipe[deactivate-service7dbbc6a9-4492-4633-80c8-ec4ef81f50dd]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service7dbbc6a9-4492-4633-80c8-ec4ef81f50dd]") }
end
