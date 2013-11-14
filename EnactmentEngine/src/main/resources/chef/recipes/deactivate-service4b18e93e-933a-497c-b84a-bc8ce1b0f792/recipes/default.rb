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

#ruby_block "disable-service4b18e93e-933a-497c-b84a-bc8ce1b0f792" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service4b18e93e-933a-497c-b84a-bc8ce1b0f792::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['4b18e93e-933a-497c-b84a-bc8ce1b0f792']['InstallationDir']}/service-4b18e93e-933a-497c-b84a-bc8ce1b0f792.jar]", :immediately
#end

ruby_block "remove-service4b18e93e-933a-497c-b84a-bc8ce1b0f792" do
  block do
  	node.run_list.remove("recipe[service4b18e93e-933a-497c-b84a-bc8ce1b0f792::jar]")
  end
  only_if { node.run_list.include?("recipe[service4b18e93e-933a-497c-b84a-bc8ce1b0f792::jar]") }
end

ruby_block "remove-deactivate-service4b18e93e-933a-497c-b84a-bc8ce1b0f792" do
  block do
    node.run_list.remove("recipe[deactivate-service4b18e93e-933a-497c-b84a-bc8ce1b0f792]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service4b18e93e-933a-497c-b84a-bc8ce1b0f792]") }
end
