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

#ruby_block "disable-servicedbd77932-26ef-4e87-9e5b-568745ead78b" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[servicedbd77932-26ef-4e87-9e5b-568745ead78b::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['dbd77932-26ef-4e87-9e5b-568745ead78b']['InstallationDir']}/service-dbd77932-26ef-4e87-9e5b-568745ead78b.jar]", :immediately
#end

ruby_block "remove-servicedbd77932-26ef-4e87-9e5b-568745ead78b" do
  block do
  	node.run_list.remove("recipe[servicedbd77932-26ef-4e87-9e5b-568745ead78b::jar]")
  end
  only_if { node.run_list.include?("recipe[servicedbd77932-26ef-4e87-9e5b-568745ead78b::jar]") }
end

ruby_block "remove-deactivate-servicedbd77932-26ef-4e87-9e5b-568745ead78b" do
  block do
    node.run_list.remove("recipe[deactivate-servicedbd77932-26ef-4e87-9e5b-568745ead78b]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicedbd77932-26ef-4e87-9e5b-568745ead78b]") }
end
