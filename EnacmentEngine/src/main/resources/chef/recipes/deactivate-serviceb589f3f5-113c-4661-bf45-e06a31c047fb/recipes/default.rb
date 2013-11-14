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

ruby_block "remove-serviceb589f3f5-113c-4661-bf45-e06a31c047fb" do
  block do
  	node.run_list.remove("recipe[serviceb589f3f5-113c-4661-bf45-e06a31c047fb::jar]")
  end
  only_if { node.run_list.include?("recipe[serviceb589f3f5-113c-4661-bf45-e06a31c047fb::jar]") }
end

ruby_block "remove-deactivate-serviceb589f3f5-113c-4661-bf45-e06a31c047fb" do
  block do
    node.run_list.remove("recipe[deactivate-serviceb589f3f5-113c-4661-bf45-e06a31c047fb]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceb589f3f5-113c-4661-bf45-e06a31c047fb]") }
  notifies :stop, "service[service_b589f3f5-113c-4661-bf45-e06a31c047fb_jar]", :immediately
end
