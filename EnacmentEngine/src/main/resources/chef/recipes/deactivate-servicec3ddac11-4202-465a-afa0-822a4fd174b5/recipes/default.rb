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

ruby_block "remove-servicec3ddac11-4202-465a-afa0-822a4fd174b5" do
  block do
  	node.run_list.remove("recipe[servicec3ddac11-4202-465a-afa0-822a4fd174b5::jar]")
  end
  only_if { node.run_list.include?("recipe[servicec3ddac11-4202-465a-afa0-822a4fd174b5::jar]") }
end

ruby_block "remove-deactivate-servicec3ddac11-4202-465a-afa0-822a4fd174b5" do
  block do
    node.run_list.remove("recipe[deactivate-servicec3ddac11-4202-465a-afa0-822a4fd174b5]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicec3ddac11-4202-465a-afa0-822a4fd174b5]") }
  notifies :stop, "service[service_c3ddac11-4202-465a-afa0-822a4fd174b5_jar]", :immediately
end
