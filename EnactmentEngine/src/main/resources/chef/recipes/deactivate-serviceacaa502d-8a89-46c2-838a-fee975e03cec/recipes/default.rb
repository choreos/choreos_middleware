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

ruby_block "disable-serviceacaa502d-8a89-46c2-838a-fee975e03cec" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[serviceacaa502d-8a89-46c2-838a-fee975e03cec::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['acaa502d-8a89-46c2-838a-fee975e03cec']['InstallationDir']}/service-acaa502d-8a89-46c2-838a-fee975e03cec.jar]", :immediately
end

ruby_block "remove-serviceacaa502d-8a89-46c2-838a-fee975e03cec" do
  block do
  	node.run_list.remove("recipe[serviceacaa502d-8a89-46c2-838a-fee975e03cec::jar]")
  end
  only_if { node.run_list.include?("recipe[serviceacaa502d-8a89-46c2-838a-fee975e03cec::jar]") }
end

ruby_block "remove-deactivate-serviceacaa502d-8a89-46c2-838a-fee975e03cec" do
  block do
    node.run_list.remove("recipe[deactivate-serviceacaa502d-8a89-46c2-838a-fee975e03cec]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceacaa502d-8a89-46c2-838a-fee975e03cec]") }
end
