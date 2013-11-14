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

#ruby_block "disable-service8ace3baf-f13b-4512-922e-23e2e8397283" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service8ace3baf-f13b-4512-922e-23e2e8397283::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['8ace3baf-f13b-4512-922e-23e2e8397283']['InstallationDir']}/service-8ace3baf-f13b-4512-922e-23e2e8397283.jar]", :immediately
#end

ruby_block "remove-service8ace3baf-f13b-4512-922e-23e2e8397283" do
  block do
  	node.run_list.remove("recipe[service8ace3baf-f13b-4512-922e-23e2e8397283::jar]")
  end
  only_if { node.run_list.include?("recipe[service8ace3baf-f13b-4512-922e-23e2e8397283::jar]") }
end

ruby_block "remove-deactivate-service8ace3baf-f13b-4512-922e-23e2e8397283" do
  block do
    node.run_list.remove("recipe[deactivate-service8ace3baf-f13b-4512-922e-23e2e8397283]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service8ace3baf-f13b-4512-922e-23e2e8397283]") }
end
