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

ruby_block "disable-servicea6844fd9-7fcd-43ec-a28e-bcc973c77091" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicea6844fd9-7fcd-43ec-a28e-bcc973c77091::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['a6844fd9-7fcd-43ec-a28e-bcc973c77091']['InstallationDir']}/service-a6844fd9-7fcd-43ec-a28e-bcc973c77091.jar]", :immediately
end

ruby_block "remove-servicea6844fd9-7fcd-43ec-a28e-bcc973c77091" do
  block do
  	node.run_list.remove("recipe[servicea6844fd9-7fcd-43ec-a28e-bcc973c77091::jar]")
  end
  only_if { node.run_list.include?("recipe[servicea6844fd9-7fcd-43ec-a28e-bcc973c77091::jar]") }
end

ruby_block "remove-deactivate-servicea6844fd9-7fcd-43ec-a28e-bcc973c77091" do
  block do
    node.run_list.remove("recipe[deactivate-servicea6844fd9-7fcd-43ec-a28e-bcc973c77091]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicea6844fd9-7fcd-43ec-a28e-bcc973c77091]") }
end
