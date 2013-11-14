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

ruby_block "disable-serviceb5e65fdd-8db6-4053-bd85-4955c52f776c" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[serviceb5e65fdd-8db6-4053-bd85-4955c52f776c::jar]") }
  notifies :stop, "service[service_b5e65fdd-8db6-4053-bd85-4955c52f776c_jar]", :immediately
end

ruby_block "remove-serviceb5e65fdd-8db6-4053-bd85-4955c52f776c" do
  block do
  	node.run_list.remove("recipe[serviceb5e65fdd-8db6-4053-bd85-4955c52f776c::jar]")
  end
  only_if { node.run_list.include?("recipe[serviceb5e65fdd-8db6-4053-bd85-4955c52f776c::jar]") }
end

ruby_block "remove-deactivate-serviceb5e65fdd-8db6-4053-bd85-4955c52f776c" do
  block do
    node.run_list.remove("recipe[deactivate-serviceb5e65fdd-8db6-4053-bd85-4955c52f776c]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceb5e65fdd-8db6-4053-bd85-4955c52f776c]") }
end
