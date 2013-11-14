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

#ruby_block "disable-service7b9e6998-38e5-4fe7-821e-d83f3e19bbe6" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service7b9e6998-38e5-4fe7-821e-d83f3e19bbe6::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['7b9e6998-38e5-4fe7-821e-d83f3e19bbe6']['InstallationDir']}/service-7b9e6998-38e5-4fe7-821e-d83f3e19bbe6.jar]", :immediately
#end

ruby_block "remove-service7b9e6998-38e5-4fe7-821e-d83f3e19bbe6" do
  block do
  	node.run_list.remove("recipe[service7b9e6998-38e5-4fe7-821e-d83f3e19bbe6::jar]")
  end
  only_if { node.run_list.include?("recipe[service7b9e6998-38e5-4fe7-821e-d83f3e19bbe6::jar]") }
end

ruby_block "remove-deactivate-service7b9e6998-38e5-4fe7-821e-d83f3e19bbe6" do
  block do
    node.run_list.remove("recipe[deactivate-service7b9e6998-38e5-4fe7-821e-d83f3e19bbe6]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service7b9e6998-38e5-4fe7-821e-d83f3e19bbe6]") }
end
