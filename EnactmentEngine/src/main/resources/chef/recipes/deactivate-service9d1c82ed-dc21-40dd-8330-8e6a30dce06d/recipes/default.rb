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

ruby_block "remove-activate-service9d1c82ed-dc21-40dd-8330-8e6a30dce06d" do
  block do
    node.run_list.remove("recipe[activate-service9d1c82ed-dc21-40dd-8330-8e6a30dce06d]")
  end
  only_if { node.run_list.include?("recipe[activate-service9d1c82ed-dc21-40dd-8330-8e6a30dce06d]") }
end


ruby_block "remove-deactivate-service9d1c82ed-dc21-40dd-8330-8e6a30dce06d" do
  block do
    node.run_list.remove("recipe[deactivate-service9d1c82ed-dc21-40dd-8330-8e6a30dce06d]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service9d1c82ed-dc21-40dd-8330-8e6a30dce06d]") }
  notifies :stop, "service[service_9d1c82ed-dc21-40dd-8330-8e6a30dce06d_jar]", :immediately
end
