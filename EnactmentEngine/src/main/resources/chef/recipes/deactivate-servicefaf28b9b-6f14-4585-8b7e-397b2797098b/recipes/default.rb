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

#ruby_block "disable-servicefaf28b9b-6f14-4585-8b7e-397b2797098b" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[servicefaf28b9b-6f14-4585-8b7e-397b2797098b::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['faf28b9b-6f14-4585-8b7e-397b2797098b']['InstallationDir']}/service-faf28b9b-6f14-4585-8b7e-397b2797098b.jar]", :immediately
#end

ruby_block "remove-servicefaf28b9b-6f14-4585-8b7e-397b2797098b" do
  block do
  	node.run_list.remove("recipe[servicefaf28b9b-6f14-4585-8b7e-397b2797098b::jar]")
  end
  only_if { node.run_list.include?("recipe[servicefaf28b9b-6f14-4585-8b7e-397b2797098b::jar]") }
end

ruby_block "remove-deactivate-servicefaf28b9b-6f14-4585-8b7e-397b2797098b" do
  block do
    node.run_list.remove("recipe[deactivate-servicefaf28b9b-6f14-4585-8b7e-397b2797098b]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicefaf28b9b-6f14-4585-8b7e-397b2797098b]") }
end
