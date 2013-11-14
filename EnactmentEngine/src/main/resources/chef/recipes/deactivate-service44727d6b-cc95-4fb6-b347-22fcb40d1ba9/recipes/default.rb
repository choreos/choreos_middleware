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

#ruby_block "disable-service44727d6b-cc95-4fb6-b347-22fcb40d1ba9" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service44727d6b-cc95-4fb6-b347-22fcb40d1ba9::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['44727d6b-cc95-4fb6-b347-22fcb40d1ba9']['InstallationDir']}/service-44727d6b-cc95-4fb6-b347-22fcb40d1ba9.jar]", :immediately
#end

ruby_block "remove-service44727d6b-cc95-4fb6-b347-22fcb40d1ba9" do
  block do
  	node.run_list.remove("recipe[service44727d6b-cc95-4fb6-b347-22fcb40d1ba9::jar]")
  end
  only_if { node.run_list.include?("recipe[service44727d6b-cc95-4fb6-b347-22fcb40d1ba9::jar]") }
end

ruby_block "remove-deactivate-service44727d6b-cc95-4fb6-b347-22fcb40d1ba9" do
  block do
    node.run_list.remove("recipe[deactivate-service44727d6b-cc95-4fb6-b347-22fcb40d1ba9]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service44727d6b-cc95-4fb6-b347-22fcb40d1ba9]") }
end
