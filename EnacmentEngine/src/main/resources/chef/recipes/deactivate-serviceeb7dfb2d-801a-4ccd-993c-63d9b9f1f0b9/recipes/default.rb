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

ruby_block "disable-serviceeb7dfb2d-801a-4ccd-993c-63d9b9f1f0b9" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[serviceeb7dfb2d-801a-4ccd-993c-63d9b9f1f0b9::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['eb7dfb2d-801a-4ccd-993c-63d9b9f1f0b9']['InstallationDir']}/service-eb7dfb2d-801a-4ccd-993c-63d9b9f1f0b9.jar]", :immediately
end

ruby_block "remove-serviceeb7dfb2d-801a-4ccd-993c-63d9b9f1f0b9" do
  block do
  	node.run_list.remove("recipe[serviceeb7dfb2d-801a-4ccd-993c-63d9b9f1f0b9::jar]")
  end
  only_if { node.run_list.include?("recipe[serviceeb7dfb2d-801a-4ccd-993c-63d9b9f1f0b9::jar]") }
end

ruby_block "remove-deactivate-serviceeb7dfb2d-801a-4ccd-993c-63d9b9f1f0b9" do
  block do
    node.run_list.remove("recipe[deactivate-serviceeb7dfb2d-801a-4ccd-993c-63d9b9f1f0b9]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceeb7dfb2d-801a-4ccd-993c-63d9b9f1f0b9]") }
end
